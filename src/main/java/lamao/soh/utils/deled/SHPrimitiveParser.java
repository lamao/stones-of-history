/* 
 * SHPrimitiveParser.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import lamao.soh.utils.xmlparser.ISHXmlParser;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Parses 'scene.primitives.primitive' section of DPS scene file.
 * Constructs model and returns it in <code>spatialConstructed</code> method.
 * @author lamao
 *
 */
abstract class SHPrimitiveParser extends SHDocXMLParser
{
//	/** Normal generator used for generating vertex normals */
//	private NormalGenerator _normalGenerator = new NormalGenerator();
	
	/** Vertices for current trimesh */
	private List<Vector3f> _meshVertices = new LinkedList<Vector3f>();
	
	/** Faces for current trimesh. Each face can have its own material*/
	private Map<SHMaterialGroup, List<Integer>> _meshFaces = 
		new HashMap<SHMaterialGroup, List<Integer>>();
	
	/** Texture coords for faces (U,V) */
	private Map<SHMaterialGroup, List<Vector2f>> _texCoords = 
		new HashMap<SHMaterialGroup, List<Vector2f>>();
	
	/** Materials to be used for constructed meshes. Injected by client */
	private Map<String, SHMaterialGroup> _materials = null;
	
	/** Buffer used for storing indexes of vertices for each polygon.
	 * Can be stored the same vertex several times (e.i. it have to be copied 
	 * like in default box, where each side has its own vertices and each
	 * corner has three vertices)
	 */
	private List<Integer> _faceVertices = new LinkedList<Integer>();
	
	public SHPrimitiveParser(Map<String, SHMaterialGroup> materials)
	{
		_materials = materials;
		addParser("primitive.vertices", new VerticesParser());
		addParser("primitive.polygons.poly", new PolygonsParser());
	}
	
	@Override
	public void parse(Node node)
	{
		_meshVertices.clear();
		_meshFaces.clear();
		_texCoords.clear();
		_faceVertices.clear();
		Element primitive = (Element)node;
		
		super.parse(node);
		
		Spatial spatial = constructPrimitive();
		spatial.setName(primitive.getAttribute("name"));
		
		spatialConstructed(node, spatial);
	}

	/**
	 * Invoked when 'primitive' tag has been parsed and new spatial has been 
	 * created. Subclasses must implement this method by adding necessary 
	 * behavior.
	 * @param node
	 * @param spatial
	 */
	public abstract void spatialConstructed(Node node, Spatial spatial);
	
	/**
	 * Constructs primitive with previously initialized information 
	 * (vertices, materials etc). Primitive can have multiple materials.
	 * In such case it is <code>Node</code>. If primitive has only one
	 * material it is <code>TriMesh</code>
	 * @return
	 */
	private Spatial constructPrimitive()
	{
		Spatial primitive = null;
		
		// create vertext array
		Vector3f[] v = new Vector3f[_meshVertices.size()];
		int i = 0;
		for (Vector3f vector : _meshVertices)
		{
			v[i++] = vector;
		}
		
		// if entire primitive has single material it will be TriMesh
		// if primitive has multiple materials it will be Node of TriMeshes
		if (_meshFaces.size() == 1)
		{
			SHMaterialGroup material = (SHMaterialGroup)_meshFaces.keySet().toArray()[0];
			List<Integer> faces = _meshFaces.get(material);
			List<Vector2f> texCoords = _texCoords.get(material);
			primitive = constructGeometry(v, faces, texCoords, material);
		}
		else
		{
			primitive = new com.jme3.scene.Node();
			int meshNumber = 0;
			// create separate mesh for each material
			for (SHMaterialGroup material : _meshFaces.keySet())
			{
				// create faces array
				List<Integer> faces = _meshFaces.get(material);
				List<Vector2f> texCoords = _texCoords.get(material);
				Geometry geometry = constructGeometry(v, faces, texCoords, material);
                geometry.setName("mesh" + meshNumber++);
				((com.jme3.scene.Node)primitive).attachChild(geometry);
			}
			calculateNodeCenter((com.jme3.scene.Node) primitive);
			
		}
		primitive.setModelBound(new BoundingBox());
		primitive.updateModelBound();
		return primitive;
	}
	
	/**
	 * Calculates average local translation of all node's children,
	 * setup this point as node's local translation and correct children's
	 * local translations.<br>
	 * All routines are similar to {@link #calculateMeshCenter(Vector3f[])} 
	 * @param node node
	 */
	private void calculateNodeCenter(com.jme3.scene.Node node)
	{
		Vector3f center = new Vector3f(0, 0, 0);
		for (Spatial spatial : node.getChildren())
		{
			center.addLocal(spatial.getLocalTranslation());
		}
		center.divideLocal(node.getQuantity());
		
		for (Spatial spatial : node.getChildren())
		{
			spatial.getLocalTranslation().subtractLocal(center);
		}
	}
	
	/**
	 * Constructs single mesh using given vertices, faces, texCoords and 
	 * material
	 * @param vertices - vertices for current mesh
	 * @param faces - faces of mesh
	 * @param texCoords - texture coordinates of mesh (can be null).
	 * @param material - material of mesh
	 * @return
	 */
	private Geometry constructGeometry(Vector3f[] vertices, List<Integer> faces,
                                       List<Vector2f> texCoords, SHMaterialGroup material)
	{
		int i = 0;
		
		// create array of vertices from _faceVertices
		Vector3f[] v = new Vector3f[_faceVertices.size()];
		for (Integer index : _faceVertices)
		{
			// TODO: See if this clone really need and can't be replaced
			v[i++] = vertices[index].clone();
		}		
		Vector3f localTranslation = calculateMeshCenter(v);
		
		
		// create faces array
		int[] f = new int[faces.size()];
		i = 0;
		for (Integer vertIndex: faces)
		{
			f[i++] = vertIndex;
		}

		Vector2f[] tex = null;
		if (texCoords != null && !texCoords.isEmpty())
		{
			tex = new Vector2f[texCoords.size()];
			i = 0;
			for (Vector2f vector : texCoords)
			{
				tex[i++] = vector;
			}
		}

		Mesh mesh = new Mesh();
		FloatBuffer normals = computeNormals(v, f);
		// create trimesh
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(v));
        mesh.setBuffer(VertexBuffer.Type.Normal, 1, normals);
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(tex));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(f));

        Geometry geometry = new Geometry("", mesh);
		applyMaterial(geometry, material);

		geometry.setLocalTranslation(localTranslation);
        geometry.setModelBound(new BoundingBox());
		geometry.updateModelBound();
		
		return geometry;
	}
	
	/**
	 * Calculates average point of given array and treats it as the center
	 * of mesh that will be constructed (its local translation).<br> 
	 * <b>NOTE:</b> After calculation all vertices are
	 * corrected by the formula <code>v[i] = v[i] - center</code>
	 * @param vertices - vertices of mesh
	 * @return center point of mesh. Its local translation.
	 */
	private Vector3f calculateMeshCenter(Vector3f[] vertices)
	{
		Vector3f center = new Vector3f(0, 0, 0);
		for (Vector3f v : vertices)
		{
			center.addLocal(v);
		}
		center.divideLocal(vertices.length);
		
		for (Vector3f v : vertices)
		{
			v.subtractLocal(center);
		}
		
		return center;
	}
	
	
	/**
	 * Computes normals for each face.
	 * @param vertices - array of vertices
	 * @param faces - array of faces
	 * @return float buffer of normals
	 */
	private FloatBuffer computeNormals(Vector3f[] vertices, int[] faces) 
	{
        Vector3f vector1 = new Vector3f();
        Vector3f vector2 = new Vector3f();
        Vector3f vector3 = new Vector3f();

        // Here we allocate all the memory we need to calculate the normals
        Vector3f[] tempNormals = new Vector3f[faces.length];
        Vector3f[] normals = new Vector3f[vertices.length];

        // Go though all of the faces of this object
        for (int i = 0; i < faces.length; i += 3) 
        {
        	vector1.set(vertices[faces[i]]);
        	vector2.set(vertices[faces[i + 1]]);
        	vector3.set(vertices[faces[i + 2]]);

        	vector1.subtractLocal(vector3);

        	tempNormals[i / 3] = vector1.cross(vector3.subtract(vector2))
        			.normalizeLocal();
        }

        Vector3f sum = new Vector3f();
        int shared = 0;

        for (int i = 0; i < vertices.length; i++) 
        {
        	for (int j = 0; j < faces.length; j += 3) 
        	{
        		if (faces[j] == i
        			|| faces[j + 1] == i
        			|| faces[j + 2] == i) 
        		{
        			sum.addLocal(tempNormals[j / 3]);
        			shared++;
        		}
        	}

        	normals[i] = sum.divide((-shared)).normalizeLocal();

        	sum.zero(); // Reset the sum
        	shared = 0; // Reset the shared
        }

        return BufferUtils.createFloatBuffer(normals);
    }
	
	private void applyMaterial(Geometry geometry, SHMaterialGroup m)
	{
        if (m.m != null)
		{
            if (m.t != null)
            {
                m.m.setTexture("ColorMap", m.t);
            }
            geometry.setMaterial(m.m);
        }

	}
	
	/** Parses vertices of primitive */
	public class VerticesParser implements ISHXmlParser
	{
		@Override
		public void parse(Node node)
		{
			NodeList vertices = node.getChildNodes();
			Element vertex = null;
			for (int i = 0; i < vertices.getLength(); i++)
			{
				vertex = (Element)vertices.item(i);
				Vector3f vertexCoord = new Vector3f(
						Float.parseFloat(vertex.getAttribute("x")) / 100,
						Float.parseFloat(vertex.getAttribute("y")) / 100,
						Float.parseFloat(vertex.getAttribute("z")) / 100);
				_meshVertices.add(vertexCoord);
			}
			
		}
	}
	
	public class PolygonsParser implements ISHXmlParser
	{
		/** Material state for current face */
		private SHMaterialGroup _faceMaterial = null;
		
		@Override
		public void parse(Node node)
		{
			// get material state for current poly
			String mid = ((Element)node).getAttribute("mid");
			_faceMaterial = _materials.get(mid);
			if (_meshFaces.get(_faceMaterial) == null)
			{
				_meshFaces.put(_faceMaterial, new LinkedList<Integer>());
			}
			if (_faceMaterial.t != null && _texCoords.get(_faceMaterial) == null)
			{
				_texCoords.put(_faceMaterial, new LinkedList<Vector2f>());
			}
			
			addTriangulatedFace(node.getChildNodes());
		}
		
		/** Add new faces and perform triangulation */
		private void addTriangulatedFace(NodeList vertexList)
		{
			// copy vertices for this polygon
			int firstVertex = _faceVertices.size(); // index of first vertex for polygon
			copyVertices(vertexList);
			
			// Walk through all vertex of current face (poly)
			List<Integer> faces = _meshFaces.get(_faceMaterial);
			for (int i = 2; i < vertexList.getLength(); i++)
			{
				// store indices of vertex in _faceVertices
				// reverse order
				faces.add(firstVertex + i);
				faces.add(firstVertex + i - 1);
				faces.add(firstVertex);
			}
		}
		
		/**
		 * Copies vertices for this polygon and store texture coordinates
		 * @param polygon
		 */
		private void copyVertices(NodeList polygon)
		{
			List<Vector2f> texCoords = _texCoords.get(_faceMaterial);
			Element vertex = null;
			for (int i = 0; i < polygon.getLength(); i++)
			{
				vertex = (Element)polygon.item(i);
				_faceVertices.add(Integer.parseInt(vertex.getAttribute("vid")));
				if (_faceMaterial.t != null)
				{
					texCoords.add(new Vector2f(
							Float.parseFloat(vertex.getAttribute("u0")),
							Float.parseFloat(vertex.getAttribute("v0"))));
				}
			}
		}
	}
}
