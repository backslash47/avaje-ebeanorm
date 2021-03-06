package com.avaje.tests.transaction;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.annotation.Transactional;
import com.avaje.tests.model.basic.EBasic;
import org.junit.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class TestTransactionRollbackOnly {

  private EBasic one;

  private EBasic two;

  @Test
  public void transaction_setRollbackOnly() {

    doVia_currentTransaction();

    assertThat(one.getId()).isNotNull();
    assertThat(Ebean.find(EBasic.class, one.getId())).isNull();
  }

  @Transactional
  protected void doVia_currentTransaction() {

    one = new EBasic("WillNotSave");
    Ebean.save(one);

    Transaction transaction = Ebean.currentTransaction();
    transaction.setRollbackOnly();
  }

  @Test
  public void test_Ebean_setRollbackOnly() {

    do_Ebean_setRollbackOnly();

    assertThat(two.getId()).isNotNull();
    assertThat(Ebean.find(EBasic.class, two.getId())).isNull();
  }

  @Transactional
  protected void do_Ebean_setRollbackOnly() {

    two = new EBasic("WillNotSave");
    Ebean.save(two);

    Ebean.setRollbackOnly();
  }
}
