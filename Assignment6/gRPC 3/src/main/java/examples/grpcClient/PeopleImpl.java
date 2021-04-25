package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ArrayList;

// ###### THE INTERESTING PART #####
// Implementing the Tips service
class PeopleImpl extends PeopleGrpc.PeopleImplBase {
    URL path = TipsImpl.class.getResource("People.json");//create this file where the classes exist
    File f = new File(path.getFile());
    //BufferedReader reader = null;
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void readbyname(PeopleReadByNameRequest req, StreamObserver<PeopleResponse> responseObserver) {
        try {
            //reader = new BufferedReader(new FileReader(f));
            System.out.println("Attempting to read file");
            String filename = "People.json";
            //System.out.println(new File(""));
            InputStream is = PeopleImpl.class.getResourceAsStream(filename);
            /*if(is == null)
                throw new Exception();*/
            JSONTokener tokener = new JSONTokener(is);
            JSONObject o = new JSONObject(tokener);
            JSONArray tips = o.getJSONArray("Ids");
            JSONArray names = o.getJSONArray("Names");
            //ArrayList<Tip.Builder> tipBuilders = new ArrayList<Tip.Builder>();
            //ArrayList<Tip> tipsL = new ArrayList<Tip>();
            PeopleResponse.Builder response = PeopleResponse.newBuilder();
            for(int i = 0; i < tips.length(); i++)
            {
                //tipBuilders.add(Tip.newBuilder());
                if(req.getName().equals(names.getString(i)))
                {
                    System.out.print(names.getString(i) + ": ");//names
                    System.out.println(tips.getString(i));//ids
                    response.addName(names.getString(i));
                    response.addId(tips.getString(i));
                }
                //tipBuilders.get(i).setName(names.getString(i));
                //tipBuilders.get(i).setTip(tips.getString(i));
            }

            /*for(int i = 0; i < tips.length(); i++)
            {
                tipsL.add(tipBuilders.get(i).build());
            }
            for(int i = 0; i < tips.length(); i++)
            {
                response.addTips(tipsL.get(i));
            }*/

            //response.setIsSuccess(true);
            PeopleResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Could not access file.");
        } 
        /*System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }     
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void readbyid(PeopleReadByIdRequest req, StreamObserver<PeopleResponse> responseObserver) {
        try {
            //reader = new BufferedReader(new FileReader(f));
            System.out.println("Attempting to read file");
            String filename = "People.json";
            //System.out.println(new File(""));
            InputStream is = PeopleImpl.class.getResourceAsStream(filename);
            /*if(is == null)
                throw new Exception();*/
            JSONTokener tokener = new JSONTokener(is);
            JSONObject o = new JSONObject(tokener);
            JSONArray tips = o.getJSONArray("Ids");
            JSONArray names = o.getJSONArray("Names");
            //ArrayList<Tip.Builder> tipBuilders = new ArrayList<Tip.Builder>();
            //ArrayList<Tip> tipsL = new ArrayList<Tip>();
            PeopleResponse.Builder response = PeopleResponse.newBuilder();
            for(int i = 0; i < tips.length(); i++)
            {
                //tipBuilders.add(Tip.newBuilder());
                if(req.getId().equals(tips.getString(i)))
                {
                    System.out.print(names.getString(i) + ": ");//names
                    System.out.println(tips.getString(i));//ids
                    response.addName(names.getString(i));
                    response.addId(tips.getString(i));
                }
                //tipBuilders.get(i).setName(names.getString(i));
                //tipBuilders.get(i).setTip(tips.getString(i));
            }

            /*for(int i = 0; i < tips.length(); i++)
            {
                tipsL.add(tipBuilders.get(i).build());
            }
            for(int i = 0; i < tips.length(); i++)
            {
                response.addTips(tipsL.get(i));
            }*/

            //response.setIsSuccess(true);
            PeopleResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Could not access file.");
        } 
        /*System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }
}