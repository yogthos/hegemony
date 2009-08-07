package network;
		
	import java.util.HashMap;
	import java.util.Map;
	import org.jivesoftware.smack.ConnectionConfiguration;
	import org.jivesoftware.smack.PacketListener;
	import org.jivesoftware.smack.XMPPConnection;
	import org.jivesoftware.smack.filter.PacketTypeFilter;
	import org.jivesoftware.smack.packet.Message;
	import org.jivesoftware.smack.packet.Packet;
	import org.jivesoftware.smack.packet.Presence;


	public enum ConnectionManager {

	    INSTANCE;
	    private Map<String, XMPPConnection> connections = new HashMap<String, XMPPConnection>();
	    private String[] authorizedUsers = {};
	    private String host = "talk.google.com";
	    private int port = 5222;
	    private String type = "gmail.com";
	    int reconnectTimeout = 30000;

	    /**
	     * 
	     * @param userId - the id of the bot for which the connection is created
	     * @param password - the password used to login
	     * @throws java.lang.Exception thrown if any errors are encountered when establishing the connection
	     */
	    public void getConnection(String userId, String password) throws InterruptedException {
	        
	        try {
	            host = System.getProperty("HOST");
	            port = Integer.parseInt(System.getProperty("PORT").trim());
	            type = System.getProperty("TYPE");
	            String authorizedUsersString = System.getProperty("AUTHORIZED_USERS");
	            if (null != authorizedUsersString)
	                authorizedUsers = authorizedUsersString.split(",");
	            
	            reconnectTimeout = Integer.parseInt(System.getProperty("RECONNECT_TIMEOUT").trim()) * 1000;

	        } catch (Exception ex) {
	            System.exit(1);
	        }

	        ConnectionConfiguration connConfig = new ConnectionConfiguration(host, port, type);
	        XMPPConnection connection = new XMPPConnection(connConfig);
	        
	        //connect to server
	        try {
	            connection.connect();
	        } catch (Exception ex) {
	            return;
	        }
	        //login to server
	        try {
	            if (userId.contains("@"))
	                userId = userId.substring(0, userId.indexOf("@"));
	            
	            connection.login(userId, password);

	            Presence presence = new Presence(Presence.Type.available);
	            connection.sendPacket(presence);

	        } catch (Exception ex) {
	            return;
	        }
	   
	        addPacketListener(connection);
	        connections.put(userId, connection);
	    }

	    
	    public void closeConnection(String userId) {
	        XMPPConnection connection = connections.get(userId);

	        if (null == connection) {
	            return;
	        }

	        if (connection.isConnected()) {
	            connection.disconnect();
	        }
	    }

	    /**
	     * 
	     * @param userId - checks if the user has any chat connections associated with its id
	     * @return true if connection exists
	     */
	    public boolean isConnected(String userId) {
	        XMPPConnection connection = connections.get(userId);
	        if (null == connection) {
	            return false;
	        }
	        return connection.isConnected();
	    }

	    //creates a listener for the connection to receive messages

	    private void addPacketListener(XMPPConnection connection) {
	        PacketListener listener = new PacketListener() {

	                    public void processPacket(Packet packet) {
	                        if (packet instanceof Message) {
	                            Message msg = (Message) packet;
	                            if (msg.getType().equals(Message.Type.chat) && msg.getBody() != null) {
	                                // Process message
	                                String remoteUser = msg.getFrom();

	                                //check if the user is valid
	                                if (authorizedUsers.length > 0) {
	                                    boolean authorized = false;
	                                    for (String user : authorizedUsers) {
	                                        if (user.trim().equals(remoteUser.substring(0, remoteUser.indexOf("/")))) {
	                                            authorized = true;
	                                        }
	                                    }
	                                    if (!authorized) {
	                                        return;
	                                    }
	                                }

	                                //user is ok, process the message contents and see if we have an applicable plugin
	                                //TODO: handle incoming player action
	                            }
	                        }
	                    }
	                };
	        // Register the listener.
	        connection.addPacketListener(listener, new PacketTypeFilter(Message.class));
	    }

	    /**
	     * 
	     * @param userId - the id of the bot initiating the message
	     * @param targetUser - the user to whom the message will be sent
	     * @param message - the message which will be sent to the targetUser
	     * @throws java.lang.Exception thrown when no connection exists for the user
	     */
	    public synchronized void sendMessage(String userId, String targetUser, String message) {

	        if (userId.contains("@"))
	            userId = userId.substring(0, userId.indexOf("@"));
	            
	        XMPPConnection connection = connections.get(userId);
	        if (null == connection) {
	            return;
	        }

	        // google bounces back the default message types, you must use chat
	        Message msg = new Message(targetUser, Message.Type.chat);
	        msg.setBody(message);
	        connection.sendPacket(msg);
	    }
	}