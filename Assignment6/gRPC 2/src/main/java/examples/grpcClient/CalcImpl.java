package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;

// ###### THE INTERESTING PART #####
// Implementing the Echo service
class CalcImpl extends CalcGrpc.CalcImplBase {
    
    // We only defined one service so we only overwrite one method, we just echo back the client message
    /*@Override
    public void add(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }     
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void subtract(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Received from client: " + req.getMessage());
        ServerResponse response = ServerResponse.newBuilder().setMessage(req.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    } 
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