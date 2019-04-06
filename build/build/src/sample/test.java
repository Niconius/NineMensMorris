package sample;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Player eins = new Player("white",1);
		Player zwei = new Player("black",1);
		Player drei = eins;
		drei =zwei;
		System.out.println(eins.getColor());
	}

}
