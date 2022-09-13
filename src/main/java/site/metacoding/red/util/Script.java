package site.metacoding.red.util;

public class Script {

	public static String back(String msg) {	//뒤로가기
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("<script>");
		return sb.toString();
	}
	
	public static String href(String url) {	//이동
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("location.href='"+ url+"';");
		sb.append("<script>");
		return sb.toString();
	}
	
	public static String href(String url, String msg) {	//오버로딩
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("location.href='"+ url+"';");
		sb.append("<script>");
		return sb.toString();
	}
}