package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;
import java.util.ArrayList;

// ###### THE INTERESTING PART #####
// Implementing the Calc service
class CalcImpl extends CalcGrpc.CalcImplBase {
    @Override
    public void add(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
    	double result = 0;
        CalcResponse.Builder response = CalcResponse.newBuilder();
        response.setIsSuccess(true);
        for (double number : req.getNumList()) {
        	System.out.println(number);
      		result += number;
    	}
    	System.out.println(result);
    	response.setSolution(result);
    	CalcResponse resp = response.build();
    	//response.setSolution(result);
    	responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }     
    @Override
    public void subtract(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        double result = 0;
        boolean first = false;
        CalcResponse.Builder response = CalcResponse.newBuilder();
        response.setIsSuccess(true);
        for (double number : req.getNumList()) {
        	System.out.println(number);
        	if(!first) {
        		result = number;
        		first = true;
        	}
        	else
      			result -= number;
    	}
    	System.out.println(result);
    	response.setSolution(result);
    	CalcResponse resp = response.build();
    	//response.setSolution(result);
    	responseObserver.onNext(resp);
        responseObserver.onCompleted();
    } 
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void multiply(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        double result = 1;
        CalcResponse.Builder response = CalcResponse.newBuilder();
        response.setIsSuccess(true);
        for (double number : req.getNumList()) {
        	System.out.println(number);
      			result *= number;
    	}
    	System.out.println(result);
    	response.setSolution(result);
    	CalcResponse resp = response.build();
    	//response.setSolution(result);
    	responseObserver.onNext(resp);
        responseObserver.onCompleted();
    } 
    // We only defined one service so we only overwrite one method, we just echo back the client message
    @Override
    public void divide(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        boolean first = false;
        double firstNum = 0;
        double sum = 0;
        double result = 0;
        CalcResponse.Builder response = CalcResponse.newBuilder();
        response.setIsSuccess(true);
        for (double number : req.getNumList()) {
        	System.out.println(number);
        	if(!first) {
        		firstNum = number;
        		first = true;
        	}
        	else
      			sum += number;
    	}
    	if(sum == 0)
    		response.setIsSuccess(false);
    	else
    		result = firstNum / sum;
    	System.out.println(result);
    	response.setSolution(result);
    	CalcResponse resp = response.build();
    	//response.setSolution(result);
    	responseObserver.onNext(resp);
        responseObserver.onCompleted();
    } 
}