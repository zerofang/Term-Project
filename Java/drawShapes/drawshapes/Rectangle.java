package drawshapes;

public class Rectangle extends Shape{
	private String name;
	
	public Rectangle(String name) {
		this.name = name;
	}
	
	@Override
	public String getType() {
		return "Rectangle";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void drawShape() {
		System.out.println("* * * * *");
		System.out.println("* * * * *");
		System.out.println("* * * * *");
		System.out.println("* * * * *");
		System.out.println();
	}

}
