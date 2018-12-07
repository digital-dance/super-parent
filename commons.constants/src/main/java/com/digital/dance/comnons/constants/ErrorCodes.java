package com.digital.dance.comnons.constants;

public abstract interface ErrorCodes
{
  public static final Integer NOMAL = Integer.valueOf(0);

  public static final Integer SYS_OR_NET_ERROR = Integer.valueOf(100001);

  public static final Integer DB_ERROR = Integer.valueOf(101001);

  public static final Integer UNKNOWN_ERROR = Integer.valueOf(102001);

  public static final Integer DB_EXCEPTION = Integer.valueOf(103001);

  public static final Integer REQUEST_PARAM_EXCEPTION = Integer.valueOf(103002);

  public static final Integer RESPONSE_PARAM_EXCEPTION = Integer.valueOf(103003);

  public static final Integer INTERNAL_EXCEPTION = Integer.valueOf(1004001);

  public static final Integer NO_SUCH_REQUEST_HANDLING_METHOD_EXCEPTION = Integer.valueOf(1004002);

  public static final Integer HTTPREQUEST_METHOD_NOTSUPPORTED_EXCEPTION = Integer.valueOf(1004003);

  public static final Integer HTTP_MEDIATYPE_NOTSUPPORTED_EXCEPTION = Integer.valueOf(1004004);

  public static final Integer HTTP_MEDIATYPE_NOTACCEPTABLE_EXCEPTION = Integer.valueOf(1004005);

  public static final Integer MISSING_PATH_VARIABL_EEXCEPTION = Integer.valueOf(1004006);

  public static final Integer MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = Integer.valueOf(1004007);

  public static final Integer SERVLETREQUEST_BINDING_EXCEPTION = Integer.valueOf(1004008);

  public static final Integer CONVERSION_NOTSUPPORTED_EXCEPTION = Integer.valueOf(1004009);

  public static final Integer TYPEM_ISMATCH_EXCEPTION = Integer.valueOf(1004010);

  public static final Integer HTTP_MESSAGE_NOTREADABLE_EXCEPTION = Integer.valueOf(1004011);

  public static final Integer HTTP_MESSAGE_NOTWRITABLE_EXCEPTION = Integer.valueOf(1004012);

  public static final Integer METHOD_ARGUMENT_NOTVALID_EXCEPTION = Integer.valueOf(1004013);

  public static final Integer MISSING_SERVLET_REQUESTPART_EXCEPTION = Integer.valueOf(1004014);

  public static final Integer BIND_EXCEPTION = Integer.valueOf(1004015);

  public static final Integer REPEAT_SUBMIT = Integer.valueOf(1004016);
}
