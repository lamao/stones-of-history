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

import lamao.soh.utils.xmlparser.ISHXmlParser;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jme.scene.TexCoords;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;

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
			primitive = constructTriMesh(v, faces, texCoords, material);
		}
		else
		{
			primitive = new com.jme.scene.Node();
			int meshNumber = 0;
			// create separate mesh for each material
			for (SHMaterialGroup material : _meshFaces.keySet())
			{
				// create faces array
				List<Integer> faces = _meshFaces.get(material);
				List<Vector2f> texCoords = _texCoords.get(material);
				TriMesh mesh = constructTriMesh(v, faces, texCoords, material);
				mesh.setName("mesh" + meshNumber++);
				((com.jme.scene.Node)primitive).attachChild(mesh);
			}
		}
//		GeometryTool.minimizeVerts(_triMesh, GeometryTool.MV_SAME_COLORS | 
//				GeometryTool.MV_SAME_NORMALS);
		primitive.setModelBound(new BoundingBox());
		primitive.updateModelBound();
		return primitive;
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
	private TriMesh constructTriMesh(Vector3f[] vertices, List<Integer> faces, 
			List<Vector2f> texCoords, SHMaterialGroup material)
	{
		int i = 0;
		
		// create array of vertices from _faceVertices
		Vector3f[] v = new Vector3f[_faceVertices.size()];
		for (Integer index : _faceVertices)
		{
			v[i++] = vertices[index];
		}
		
		
		i = 0;
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

		TriMesh mesh = new TriMesh();
		FloatBuffer normals = computeNormals(v, f);
		// create trimesh
		mesh.reconstruct(BufferUtils.createFloatBuffer(v), normals, null, 
				TexCoords.makeNew(tex), 
				BufferUtils.createIntBuffer(f));

//		_normalGenerator.generateNormals(mesh, FastMath.PI / 4);
		applyMaterial(mesh, material);
		mesh.updateRenderState();

		mesh.setModelBound(new BoundingBox());
		mesh.updateModelBound();
		
		return mesh;
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
	
	private void applyMaterial(TriMesh mesh, SHMaterialGroup m)
	{
		if (m.m != null)
		{
			mesh.setRenderState(m.m);
		}
		if (m.as != null)
		{
			mesh.setRenderState(m.as);
			mesh.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		}
		if (m.ts != null)
		{
			mesh.setRenderState(m.ts);
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
			// TODO: Create separate node for each material state inside
			// primitive
			String mid = ((Element)node).getAttribute("mid");
			_faceMaterial = _materials.get(mid);
			if (_meshFaces.get(_faceMaterial) == null)
			{
				_meshFaces.put(_faceMaterial, new LinkedList<Integer>());
			}
			if (_faceMaterial.ts != null && _texCoords.get(_faceMaterial) == null)
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
				if (_faceMaterial.ts != null)
				{
					texCoords.add(new Vector2f(
							Float.parseFloat(vertex.getAttribute("u0")),
							Float.parseFloat(vertex.getAttribute("v0"))));
				}
			}
		}
	}
}
