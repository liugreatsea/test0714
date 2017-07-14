/*******************************************************************************
 * Copyright (c) 2017 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/ 
package wasdev.sample.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import wasdev.sample.Visitor;
import wasdev.sample.store.VisitorStore;
import wasdev.sample.store.VisitorStoreFactory;

@ApplicationPath("api")
@Path("/visitors")
public class VisitorAPI extends Application {
	
	//Our database store
	VisitorStore store = VisitorStoreFactory.getInstance();

  /**
   * Gets all Visitors.
   * REST API example:
   * <code>
   * GET http://localhost:9080/GetStartedJava/api/visitors
   * </code>
   * 
   * Response:
   * <code>
   * [ "Bob", "Jane" ]
   * </code>
   * @return A collection of all the Visitors
   */
    @GET
    @Path("/")
    @Produces({"application/json;charset=utf-8"})
    public String getVisitors() {
//    	
    	ConversationService service = new ConversationService("2017-05-26");
    	service.setUsernameAndPassword("fa99fc62-40b0-4de6-ae0e-b7ffdcb83b5e", "mvNWsOg7sQ17");


    	MessageRequest newMessage = new MessageRequest.Builder()
    	  .inputText("")
    	  // Replace with the context obtained from the initial request
    	  //.context(...)
    	  .build();

    	String workspaceId = "be97e200-78b8-4d9d-bdf5-31ae019f2414";

    	MessageResponse response = service
    	  .message(workspaceId, newMessage)
    	  .execute();

    	System.out.println(response);
    	
    	return response.toString();
//    	
//    	JsonObject jsonobj = new JsonObject();  
//		jsonobj.addProperty("name", "Test");
//		return jsonobj.toString();
    	
//    	try{  
//    		Class.forName("com.ibm.DB2.jcc.DB2Driver");  
//    		Connection con=DriverManager.getConnection(  
//    		"jdbc:db2://dashdb-entry-yp-dal09-08.services.dal.bluemix.net:50000/BLUDB","dash11102","JmM_Mr_E3rn7");  
//    		Statement stmt=con.createStatement();  
//    		ResultSet rs=stmt.executeQuery("select * from TEST");  
//    		while(rs.next())  
//    		System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
//    		con.close();  
//    		}catch(Exception e){ 
//    			System.out.println(e);}  
//    		
//    	//***********************
// 		
//		if (store == null) {
//			JsonObject jsonobj = new JsonObject();  
//			jsonobj.addProperty("name", "Bob");
//			return jsonobj.toString();
//		}
//		
//		List<String> names = new ArrayList<String>();
//		for (Visitor doc : store.getAll()) {
//			String name = doc.getName();
//			if (name != null){
//				names.add(name);
//			}
//		}
//		return new Gson().toJson(names);
    }
    
    /**
     * Creates a new Visitor.
     * 
     * REST API example:
     * <code>
     * POST http://localhost:9080/GetStartedJava/api/visitors
     * <code>
     * POST Body:
     * <code>
     * {
     *   "name":"Bob"
     * }
     * </code>
     * Response:
     * <code>
     * {
     *   "id":"123",
     *   "name":"Bob"
     * }
     * </code>
     * @param visitor The new Visitor to create.
     * @return The Visitor after it has been stored.  This will include a unique ID for the Visitor.
     */
    @POST
    @Produces("application/text")
    @Consumes("application/json")
    public String newToDo(Visitor visitor) {
      if(store == null) {
    	  return String.format("Hello %s!", visitor.getName());
      }
      store.persist(visitor);
      return String.format("Hello %s! I've added you to the database.", visitor.getName());

    }

}