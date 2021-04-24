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
class TipsImpl extends TipsGrpc.TipsImplBase {
    URL path = TipsImpl.class.getResource("TipsFile.json");
    File f = new File(path.getFile());
    //BufferedReader reader = null;
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void read(Empty e, StreamObserver<TipsReadResponse> responseObserver) {
        try {
            //reader = new BufferedReader(new FileReader(f));
            System.out.println("Attempting to read file");
            String filename = "TipsFile.json";
            //System.out.println(new File(""));
            InputStream is = TipsImpl.class.getResourceAsStream(filename);
            /*if(is == null)
                throw new Exception();*/
            JSONTokener tokener = new JSONTokener(is);
            JSONObject o = new JSONObject(tokener);
            JSONArray tips = o.getJSONArray("Tips");
            JSONArray names = o.getJSONArray("Names");
            ArrayList<Tip.Builder> tipBuilders = new ArrayList<Tip.Builder>();
            ArrayList<Tip> tipsL = new ArrayList<Tip>();
            TipsReadResponse.Builder response = TipsReadResponse.newBuilder();
            for(int i = 0; i < tips.length(); i++)
            {
                tipBuilders.add(Tip.newBuilder());

                System.out.print(names.getString(i) + ": ");
                System.out.println(tips.getString(i));

                tipBuilders.get(i).setName(names.getString(i));
                tipBuilders.get(i).setTip(tips.getString(i));
            }

            for(int i = 0; i < tips.length(); i++)
            {
                tipsL.add(tipBuilders.get(i).build());
            }
            for(int i = 0; i < tips.length(); i++)
            {
                response.addTips(tipsL.get(i));
            }

            response.setIsSuccess(true);
            TipsReadResponse resp = response.build();
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
    public void write(TipsWriteRequest req, StreamObserver<TipsWriteResponse> responseObserver) {
        //reader = new BufferedReader(new FileReader(f));
        try{
            System.out.println("Attempting to write to file");
            String filename = "TipsFile.json";
            //System.out.println(new File(""));
            InputStream is = TipsImpl.class.getResourceAsStream(filename);
            /*if(is == null)
                throw new Exception();*/
            JSONTokener tokener = new JSONTokener(is);
            JSONObject o = new JSONObject(tokener);
            JSONArray tips = o.getJSONArray("Tips");
            JSONArray names = o.getJSONArray("Names");
            System.out.println(req.getTip().getName());
            System.out.println(req.getTip().getTip());
            names.put(req.getTip().getName());
            tips.put(req.getTip().getTip());
            o.put("Names", names);
            o.put("Tips", tips);
            String pathname = TipsImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "example/grpcclient";
            pathname = pathname.replaceAll("%20", " ");
            System.out.println(pathname);
            FileWriter file = new FileWriter(new File(pathname, filename));
            /*file.write("{");
            file.write("\"Names\":[");
            for(int i = 0; i < tips.length(); i++)
            {
                if(i != tips.length() - 1)
                    file.write("\"TestName\", ");
                else
                    file.write("\"TestName\"");
            }
            file.write("],\nTips:[");
            for(int i = 0; i < tips.length(); i++)
            {
                if(i != tips.length() - 1)
                    file.write("\"" + tips.getString(i) + "\", ");
                else
                    file.write("\"" + tips.getString(i) + "\"");
            }
            file.write("]");
            file.write("}");*/
            file.write(o.toString());
            file.flush();
            file.close();

            TipsWriteResponse.Builder response = TipsWriteResponse.newBuilder();

            response.setIsSuccess(true);
            TipsWriteResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Could not access file.");
        } 
    } /*
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void multiply(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    } 
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void divide(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    } */
}