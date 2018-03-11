package drawshapes;

public class Triangle extends Shape{
	private String name;
	
	public Triangle(String name) {
		this.name = name;
	}
	
	@Override
	public String getType() {
		return "Triangle";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void drawShape() { 
		System.out.println("    *    ");
		System.out.println("  * * *  ");
		System.out.println(" * * * * ");
		System.out.println("* * * * *");
		System.out.println();
	}   
}
