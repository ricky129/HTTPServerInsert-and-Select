// java -cp mysql-connector-j-8.0.32.jar:. httpserverselect
// curl -X POST -d "username=utente' OR 1=1; -- " http://192.168.19.38:80

import java.io.*;
import java.net.*;
import java.sql.*;

public class httpserverselect {
	
    public static void main(String[] args) throws Exception {
		
        int port = 80;
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.17/injection", "root", ";2=BRhi*LoRI");
			
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server avviato sulla porta " + port + " ...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Leggi la richiesta inviata dal client
            StringBuilder request = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                request.append(line).append("\n");
            }

            // Se la richiesta è una richiesta POST, visualizza il testo inserito dall'utente
            if (request.toString().contains("POST")) {
				
	            System.out.println("Richiesta del client:");
                System.out.println(request.toString());
                int contentLengthIndex = request.indexOf("Content-Length:");
                int contentLength = Integer.parseInt(request.substring(contentLengthIndex + 16, request.indexOf("\n", contentLengthIndex)));
                StringBuilder requestBody = new StringBuilder();
                for (int i = 0; i < contentLength; i++) {
                    requestBody.append((char) in.read());
                }
                String userInput = requestBody.toString();
                				
				String username = userInput.replace("username=","");
                String password = "";
				
				String query = "SELECT * FROM users WHERE username = '"+username+"';";
				Statement stmt = conn.createStatement();
				System.out.println(query);
				ResultSet rs = stmt.executeQuery(query);
				
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: text/html");
				out.println("\r\n");
     			out.println("<!DOCTYPE html>");
				out.println("<html lang=\"en\">");
				out.println("<head>");
				out.println("    <meta charset=\"UTF-8\">");
				out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
				out.println("    <title>Risultato della ricerca</title>");
				out.println("<style>");
				out.println("    label {");
				out.println("        display: inline-block;");
				out.println("        width: 100px; /* Larghezza fissa per le etichette */");
				out.println("        text-align: right; /* Allineamento del testo a destra */");
				out.println("    }");
				out.println("</style>");				
				out.println("</head>");
				out.println("<body>");
				out.println("    <h1>Ricerca utente, risultati: </h1>");
	            while (rs.next()) {
					username = rs.getString("username");
					password = rs.getString("password");
    				out.println("    <label>User:</label><br>");
	    			out.println("    <label>"+username+"</label><br><br>");
		    		out.println("    <label>Password:</label><br>");
			    	out.println("    <label>"+password+"</label><br><br>");
                }

				out.println("</body>");
				out.println("</html>");				
				
				stmt.close();
				
            }else{
				
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: text/html");
				out.println("\r\n");
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Form di ricerca</title>");
				out.println("<style>");
				out.println("    label {");
				out.println("        display: inline-block;");
				out.println("        width: 100px; /* Larghezza fissa per le etichette */");
				out.println("        text-align: right; /* Allineamento del testo a destra */");
				out.println("    }");
				out.println("    input {");
				out.println("        display: inline-block;");
				out.println("        width: 200px; /* Larghezza fissa per gli input */");
				out.println("    }");
				out.println("</style>");			
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Form di ricerca</h1>");
				out.println("<form method=\"post\">");
				out.println("<label for=\"username\">Nome Utente:</label>");
				out.println("<pre><input type=\"text\" id=\"username\" name=\"username\" accept-charset=\"UTF-8\"></pre><br><br>");
				out.println("<input type=\"submit\" value=\"Invia\">");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			}
			
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
