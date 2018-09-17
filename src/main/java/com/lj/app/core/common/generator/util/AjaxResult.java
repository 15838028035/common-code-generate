package com.lj.app.core.common.generator.util;

/**
 * ajax请求结果类
 *
 */
public class AjaxResult {

  /**
   * 操作结果
   */
  private String opResult;

  /**
   * 操作失败消息
   */
  private String optFailureMsg;

  public String getOpResult() {
    return opResult;
  }

  public void setOpResult(String opResult) {
    this.opResult = opResult;
  }

  public String getOptFailureMsg() {
    return optFailureMsg;
  }

  public void setOptFailureMsg(String optFailureMsg) {
    this.optFailureMsg = optFailureMsg;
  }

}