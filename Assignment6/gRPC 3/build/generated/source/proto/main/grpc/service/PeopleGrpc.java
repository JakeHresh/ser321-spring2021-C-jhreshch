package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.33.1)",
    comments = "Source: services/people.proto")
public final class PeopleGrpc {

  private PeopleGrpc() {}

  public static final String SERVICE_NAME = "services.People";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.PeopleReadByNameRequest,
      service.PeopleResponse> getReadbynameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readbyname",
      requestType = service.PeopleReadByNameRequest.class,
      responseType = service.PeopleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.PeopleReadByNameRequest,
      service.PeopleResponse> getReadbynameMethod() {
    io.grpc.MethodDescriptor<service.PeopleReadByNameRequest, service.PeopleResponse> getReadbynameMethod;
    if ((getReadbynameMethod = PeopleGrpc.getReadbynameMethod) == null) {
      synchronized (PeopleGrpc.class) {
        if ((getReadbynameMethod = PeopleGrpc.getReadbynameMethod) == null) {
          PeopleGrpc.getReadbynameMethod = getReadbynameMethod =
              io.grpc.MethodDescriptor.<service.PeopleReadByNameRequest, service.PeopleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readbyname"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PeopleReadByNameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PeopleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleMethodDescriptorSupplier("readbyname"))
              .build();
        }
      }
    }
    return getReadbynameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.PeopleReadByIdRequest,
      service.PeopleResponse> getReadbyidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readbyid",
      requestType = service.PeopleReadByIdRequest.class,
      responseType = service.PeopleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.PeopleReadByIdRequest,
      service.PeopleResponse> getReadbyidMethod() {
    io.grpc.MethodDescriptor<service.PeopleReadByIdRequest, service.PeopleResponse> getReadbyidMethod;
    if ((getReadbyidMethod = PeopleGrpc.getReadbyidMethod) == null) {
      synchronized (PeopleGrpc.class) {
        if ((getReadbyidMethod = PeopleGrpc.getReadbyidMethod) == null) {
          PeopleGrpc.getReadbyidMethod = getReadbyidMethod =
              io.grpc.MethodDescriptor.<service.PeopleReadByIdRequest, service.PeopleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readbyid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PeopleReadByIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PeopleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleMethodDescriptorSupplier("readbyid"))
              .build();
        }
      }
    }
    return getReadbyidMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PeopleStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PeopleStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PeopleStub>() {
        @java.lang.Override
        public PeopleStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PeopleStub(channel, callOptions);
        }
      };
    return PeopleStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PeopleBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PeopleBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PeopleBlockingStub>() {
        @java.lang.Override
        public PeopleBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PeopleBlockingStub(channel, callOptions);
        }
      };
    return PeopleBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PeopleFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PeopleFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PeopleFutureStub>() {
        @java.lang.Override
        public PeopleFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PeopleFutureStub(channel, callOptions);
        }
      };
    return PeopleFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PeopleImplBase implements io.grpc.BindableService {

    /**
     */
    public void readbyname(service.PeopleReadByNameRequest request,
        io.grpc.stub.StreamObserver<service.PeopleResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReadbynameMethod(), responseObserver);
    }

    /**
     */
    public void readbyid(service.PeopleReadByIdRequest request,
        io.grpc.stub.StreamObserver<service.PeopleResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReadbyidMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReadbynameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.PeopleReadByNameRequest,
                service.PeopleResponse>(
                  this, METHODID_READBYNAME)))
          .addMethod(
            getReadbyidMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.PeopleReadByIdRequest,
                service.PeopleResponse>(
                  this, METHODID_READBYID)))
          .build();
    }
  }

  /**
   */
  public static final class PeopleStub extends io.grpc.stub.AbstractAsyncStub<PeopleStub> {
    private PeopleStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PeopleStub(channel, callOptions);
    }

    /**
     */
    public void readbyname(service.PeopleReadByNameRequest request,
        io.grpc.stub.StreamObserver<service.PeopleResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadbynameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readbyid(service.PeopleReadByIdRequest request,
        io.grpc.stub.StreamObserver<service.PeopleResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadbyidMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PeopleBlockingStub extends io.grpc.stub.AbstractBlockingStub<PeopleBlockingStub> {
    private PeopleBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PeopleBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.PeopleResponse readbyname(service.PeopleReadByNameRequest request) {
      return blockingUnaryCall(
          getChannel(), getReadbynameMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.PeopleResponse readbyid(service.PeopleReadByIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getReadbyidMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PeopleFutureStub extends io.grpc.stub.AbstractFutureStub<PeopleFutureStub> {
    private PeopleFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PeopleFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.PeopleResponse> readbyname(
        service.PeopleReadByNameRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReadbynameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.PeopleResponse> readbyid(
        service.PeopleReadByIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReadbyidMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_READBYNAME = 0;
  private static final int METHODID_READBYID = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PeopleImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PeopleImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_READBYNAME:
          serviceImpl.readbyname((service.PeopleReadByNameRequest) request,
              (io.grpc.stub.StreamObserver<service.PeopleResponse>) responseObserver);
          break;
        case METHODID_READBYID:
          serviceImpl.readbyid((service.PeopleReadByIdRequest) request,
              (io.grpc.stub.StreamObserver<service.PeopleResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PeopleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PeopleBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.StudentsProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("People");
    }
  }

  private static final class PeopleFileDescriptorSupplier
      extends PeopleBaseDescriptorSupplier {
    PeopleFileDescriptorSupplier() {}
  }

  private static final class PeopleMethodDescriptorSupplier
      extends PeopleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PeopleMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PeopleGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PeopleFileDescriptorSupplier())
              .addMethod(getReadbynameMethod())
              .addMethod(getReadbyidMethod())
              .build();
        }
      }
    }
    return result;
  }
}
