package com.ycy.dubbo.ycyspringboot.schedule.rjdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 调用接口的抽象worker类
 * **/
public abstract class InterfaceAbstractWorker<E> implements Runnable {

  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  // 删除数据比例
  protected static final double MAX_DELETE_PERCENT = 0.5;

  // 批量操作每次操作一千条数据
  protected static final int BATCH_EXECUTE_COUNT = 1000;

  private CountDownLatch downLatch;

  protected String lastExecuteDateTime;

  // 记录本地数据总量
  protected AtomicInteger localSize = new AtomicInteger(0);

  // 记录需要标记的删除数据
  protected List<E> deleteInfos = new CopyOnWriteArrayList<E>();

//  protected DalDao<E> localDao;

  public InterfaceAbstractWorker() throws Exception {
//    localDao = new DalDao<E>(getLocalClazz()) {};
  }

  @Override
  public void run() {
    long startTime = System.currentTimeMillis();
    boolean isSuccessful = false;
    logger.info("worker begin run");
    try {
      dealData();
      isSuccessful = true;
    } catch (Exception e) {
      logger.error("work run cause a exception", e);
    } finally {
      //主动清空
      clear();
      if (downLatch != null) {
        downLatch.countDown();
        logger.info("finish one countdownLatch last size is:" + downLatch.getCount());
      }
    }
    logger.info("worker finish run:" + isSuccessful + "|userTime:" + CommonUtil.getUseTime(startTime));
  }

  /**
   * 处理数据，获得数据根据增删改list选择操作
   * 
   * @throws Exception
   */
  protected void dealData() throws Exception {
    logger.info("start dealData");
    dealInsertOrUpdate();
    dealDelete();
    clear();
    logger.info("finish dealData");
  }

  /**
   * 更新或者插入数据，读接口数据形式不统一，子类各自实现
   * 
   * @throws Exception
   */
  protected abstract void dealInsertOrUpdate() throws Exception;

  /**
   * 根据deleteList删除，保护，如果删除数据量超过50%,删除无效
   * 
   * @throws Exception
   */
  protected void dealDelete() throws Exception {
   /* logger.info("start dealDelete");
    if (deleteInfos != null && !deleteInfos.isEmpty() && !((deleteInfos.size() / localSize.intValue()) > MAX_DELETE_PERCENT)) {
      try {
        localDao.batchUpdate(deleteInfos);
      } catch (Exception e) {
        logger.error("update data cause a exception", e);
        throw e;
      } finally {
        deleteInfos.clear();
      }
      logger.info("deal dealDelete size is:" + deleteInfos.size());
    }
    logger.info("finish dealDelete");*/
  }
  
  protected void clear(){
    localSize.set(0);
    deleteInfos.clear();
  }

  @SuppressWarnings("unchecked")
  private Class<E> getLocalClazz() {
    return (Class<E>) getReflectParam()[0];
  }

  private Type[] getReflectParam() {
    return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
  }

  public void clearAndAddDownLatchWorker(CountDownLatch downLatch) {
    setDownLatch(downLatch);
  }

  private void setDownLatch(CountDownLatch downLatch) {
    this.downLatch = downLatch;
  }

  public void setLastExecuteDateTime(String lastExecuteDateTime) {
    this.lastExecuteDateTime = lastExecuteDateTime;
  }

  public void init(CountDownLatch downLatch, String lastExecuteDateTime) {
    setDownLatch(downLatch);
    setLastExecuteDateTime(lastExecuteDateTime);
  }
}
