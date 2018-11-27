package cz.profinit.training.springadvanced.tradingexchange.domain;

public enum OrderSettlementState {

  /**
   * Buy order can be rejected when the user doesn't have a high enough balance.
   * This is a terminal state.
   */
  REJECTED,
  /**
   * No matching orders were found when the order was posted. It can still become {@link #SETTLED} later.
   */
  OPEN,
  /**
   * Some matching orders were found but not enough to exchange the whole order amount. It can still become {@link #SETTLED} later.
   */
  PARTIALLY_SETTLED,
  /**
   * The matching engine found enough matching orders and the whole order amount was exchanged.
   * This is a terminal state.
   */
  SETTLED
}
