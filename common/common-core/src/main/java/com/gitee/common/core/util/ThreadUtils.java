package com.gitee.common.core.util;

import java.util.List;

import com.gitee.common.core.base.Callable;
import com.gitee.common.core.thread.ThreadPool;

/**
 * 提供线程池操作类
 * 默认使用自定义的全局bsf线程池
 * @author: lihaifeng
 * @version: 2019-08-16 10:46
 **/
public class ThreadUtils {
  /**
   * 使用bsf系统线程池并行for循环
   * @param parallelCount
   * @param taskList
   * @param action
   * @param <T>
   */
  public static <T> void parallelFor(String taskName, int parallelCount, List<T> taskList,
      final Callable.Action1<T> action) {
    ThreadPool.System.parallelFor2(taskName, parallelCount, taskList, action);
  }
}
