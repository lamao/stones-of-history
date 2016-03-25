Coding style for sdaTetris is based on Java Code Conventions (http://java.sun.com/docs/codeconv/).

Differences:
  1. braces for one code block are a localed in on column with each other an with statement.
```
	if (condition)
	{
	}
```
  1. All private and protected variables starts with `'_'`.
  1. javadoc should be for every nontrivial method. It means you may not write javadoc for methods like
```
        public void setA(int a)
        {
	    _a = a;
        }
```