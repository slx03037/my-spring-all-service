corePoolSize:这个表示核心线程的数量。
maximumPoolSize:这个表示最大线程数量。
keepAliveTime:这个表示空闲线程存活时间。
unit:这个是存活时间的单位。
workQueue:这个表示工作队列，是当核心线程满了之后会存放在这里。
threadFactory:这个是线程的创建方式。
handler:这个表示拒绝策略，其有jdk提供的四种拒绝策略和一种自定义的拒绝策略



AbortPolicy	如果线程池拒绝了任务，直接报错。
CallerRunsPolicy	线程池让调用者去执行
DiscardPolicy	如果线程池拒绝了任务，直接丢弃。
DiscardOldestPolicy	如果线程池拒绝了任务，直接将工作队列中第一个任务给丢弃，将新任务入队。
自定义拒绝策略	实现RejectedExecutionHandler接口
