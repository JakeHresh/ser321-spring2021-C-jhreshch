// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/joke.proto

package service;

/**
 * <pre>
 * The response message
 * </pre>
 *
 * Protobuf type {@code services.JokeRes}
 */
public final class JokeRes extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:services.JokeRes)
    JokeResOrBuilder {
private static final long serialVersionUID = 0L;
  // Use JokeRes.newBuilder() to construct.
  private JokeRes(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private JokeRes() {
    joke_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new JokeRes();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private JokeRes(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              joke_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000001;
            }
            joke_.add(s);
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        joke_ = joke_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return service.JokeProto.internal_static_services_JokeRes_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return service.JokeProto.internal_static_services_JokeRes_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            service.JokeRes.class, service.JokeRes.Builder.class);
  }

  public static final int JOKE_FIELD_NUMBER = 1;
  private com.google.protobuf.LazyStringList joke_;
  /**
   * <code>repeated string joke = 1;</code>
   * @return A list containing the joke.
   */
  public com.google.protobuf.ProtocolStringList
      getJokeList() {
    return joke_;
  }
  /**
   * <code>repeated string joke = 1;</code>
   * @return The count of joke.
   */
  public int getJokeCount() {
    return joke_.size();
  }
  /**
   * <code>repeated string joke = 1;</code>
   * @param index The index of the element to return.
   * @return The joke at the given index.
   */
  public java.lang.String getJoke(int index) {
    return joke_.get(index);
  }
  /**
   * <code>repeated string joke = 1;</code>
   * @param index The index of the value to return.
   * @return The bytes of the joke at the given index.
   */
  public com.google.protobuf.ByteString
      getJokeBytes(int index) {
    return joke_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < joke_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, joke_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < joke_.size(); i++) {
        dataSize += computeStringSizeNoTag(joke_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getJokeList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof service.JokeRes)) {
      return super.equals(obj);
    }
    service.JokeRes other = (service.JokeRes) obj;

    if (!getJokeList()
        .equals(other.getJokeList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getJokeCount() > 0) {
      hash = (37 * hash) + JOKE_FIELD_NUMBER;
      hash = (53 * hash) + getJokeList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static service.JokeRes parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.JokeRes parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.JokeRes parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.JokeRes parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.JokeRes parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.JokeRes parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.JokeRes parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.JokeRes parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.JokeRes parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static service.JokeRes parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.JokeRes parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.JokeRes parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(service.JokeRes prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * The response message
   * </pre>
   *
   * Protobuf type {@code services.JokeRes}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:services.JokeRes)
      service.JokeResOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return service.JokeProto.internal_static_services_JokeRes_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return service.JokeProto.internal_static_services_JokeRes_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              service.JokeRes.class, service.JokeRes.Builder.class);
    }

    // Construct using service.JokeRes.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      joke_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return service.JokeProto.internal_static_services_JokeRes_descriptor;
    }

    @java.lang.Override
    public service.JokeRes getDefaultInstanceForType() {
      return service.JokeRes.getDefaultInstance();
    }

    @java.lang.Override
    public service.JokeRes build() {
      service.JokeRes result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public service.JokeRes buildPartial() {
      service.JokeRes result = new service.JokeRes(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        joke_ = joke_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.joke_ = joke_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof service.JokeRes) {
        return mergeFrom((service.JokeRes)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(service.JokeRes other) {
      if (other == service.JokeRes.getDefaultInstance()) return this;
      if (!other.joke_.isEmpty()) {
        if (joke_.isEmpty()) {
          joke_ = other.joke_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureJokeIsMutable();
          joke_.addAll(other.joke_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      service.JokeRes parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (service.JokeRes) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.LazyStringList joke_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureJokeIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        joke_ = new com.google.protobuf.LazyStringArrayList(joke_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @return A list containing the joke.
     */
    public com.google.protobuf.ProtocolStringList
        getJokeList() {
      return joke_.getUnmodifiableView();
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @return The count of joke.
     */
    public int getJokeCount() {
      return joke_.size();
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param index The index of the element to return.
     * @return The joke at the given index.
     */
    public java.lang.String getJoke(int index) {
      return joke_.get(index);
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the joke at the given index.
     */
    public com.google.protobuf.ByteString
        getJokeBytes(int index) {
      return joke_.getByteString(index);
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param index The index to set the value at.
     * @param value The joke to set.
     * @return This builder for chaining.
     */
    public Builder setJoke(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureJokeIsMutable();
      joke_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param value The joke to add.
     * @return This builder for chaining.
     */
    public Builder addJoke(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureJokeIsMutable();
      joke_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param values The joke to add.
     * @return This builder for chaining.
     */
    public Builder addAllJoke(
        java.lang.Iterable<java.lang.String> values) {
      ensureJokeIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, joke_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearJoke() {
      joke_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string joke = 1;</code>
     * @param value The bytes of the joke to add.
     * @return This builder for chaining.
     */
    public Builder addJokeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureJokeIsMutable();
      joke_.add(value);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:services.JokeRes)
  }

  // @@protoc_insertion_point(class_scope:services.JokeRes)
  private static final service.JokeRes DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new service.JokeRes();
  }

  public static service.JokeRes getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<JokeRes>
      PARSER = new com.google.protobuf.AbstractParser<JokeRes>() {
    @java.lang.Override
    public JokeRes parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new JokeRes(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<JokeRes> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<JokeRes> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public service.JokeRes getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

