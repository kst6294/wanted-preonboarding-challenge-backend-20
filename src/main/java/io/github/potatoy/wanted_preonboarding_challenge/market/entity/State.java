package io.github.potatoy.wanted_preonboarding_challenge.market.entity;

public enum State {
  /** 판매중 */
  SALE("sale"),
  /** 예약중 */
  RESERVATION("reservation"),
  /** 완료 */
  COMPLETE("complete");

  private String state;

  State(String state) {
    this.state = state;
  }
}
