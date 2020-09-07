import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.io.IOException;

import ws.WsHandler;


public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		webSocket("/ws", WsHandler.class);
		
		get("/spark/demo/test", (req, res) -> {
			return "Works";
		});
		get("book-form", (req, res) -> {
			return "Works";
		});
	}

}

