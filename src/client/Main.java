package client;

class Main {
	public static void main(String args[]) throws Exception {
        ScreenManager.getInstance().display();
        Client.connect("localhost", 9876);
	}
}