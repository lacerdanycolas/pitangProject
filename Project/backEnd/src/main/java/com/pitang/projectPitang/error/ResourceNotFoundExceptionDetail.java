package com.pitang.projectPitang.error;

public class ResourceNotFoundExceptionDetail {

  private String title;
  private int status;
  private long moment;
  private String detail;
  private String developerMessage;

  private ResourceNotFoundExceptionDetail(){

  }

  public String getTitle() {
    return title;
  }


  public int getStatus() {
    return status;
  }


  public long getMoment() {
    return moment;
  }


  public String getDetail() {
    return detail;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }


  public static final class ResourceBuilder {
    private String title;
    private int status;
    private long moment;
    private String detail;
    private String developerMessage;

    private ResourceBuilder() {
    }

    public static ResourceBuilder builder() {
      return new ResourceBuilder();
    }

    public ResourceBuilder title(String title) {
      this.title = title;
      return this;
    }

    public ResourceBuilder status(int status) {
      this.status = status;
      return this;
    }

    public ResourceBuilder moment(long moment) {
      this.moment = moment;
      return this;
    }

    public ResourceBuilder detail(String detail) {
      this.detail = detail;
      return this;
    }

    public ResourceBuilder developerMessage(String developerMessage) {
      this.developerMessage = developerMessage;
      return this;
    }

    public ResourceNotFoundExceptionDetail build() {
      ResourceNotFoundExceptionDetail resourceNotFoundExceptionDetail = new ResourceNotFoundExceptionDetail();
      resourceNotFoundExceptionDetail.status = this.status;
      resourceNotFoundExceptionDetail.detail = this.detail;
      resourceNotFoundExceptionDetail.developerMessage = this.developerMessage;
      resourceNotFoundExceptionDetail.moment = this.moment;
      resourceNotFoundExceptionDetail.title = this.title;
      return resourceNotFoundExceptionDetail;
    }


  }
}
