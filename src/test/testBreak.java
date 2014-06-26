package test;

public class testBreak {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = 20;
		int a = 19;
		int b = 9;
		while (true) {
			while (k > 0) {
				if (a > 10) {
					System.out.println(k);
					a--;
				} else {
					if (b < 5) {
						int c = 0;
						break;
					} else
						b--;
				}
				k--;
			}
			int c = 0;
		}
	}
}
