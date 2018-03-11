package drawshapes;

public class Circle extends Shape{
	private String name;
	
	public Circle(String name) {
		this.name = name;
	}
	
	@Override
	public String getType() {
		return "Circle";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void drawShape() {
		System.out.println("   * *   ");
		System.out.println(" * * * * ");
		System.out.println("* * * * *");
		System.out.println(" * * * * ");
		System.out.println("   * *   ");
		System.out.println();
	}

}
