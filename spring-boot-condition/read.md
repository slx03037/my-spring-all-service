1. @ConditionalOnBean ：当容器中有指定Bean的条件下进行实例化。
2. @ConditionalOnMissingBean ：当容器里没有指定Bean的条件下进行实例化。
3. @ConditionalOnClass ：当classpath类路径下有指定类的条件下进行实例化。
4. @ConditionalOnMissingClass ：当类路径下没有指定类的条件下进行实例化。
5. @ConditionalOnWebApplication ：当项目是一个Web项目时进行实例化。
6. @ConditionalOnNotWebApplication ：当项目不是一个Web项目时进行实例化。
7. @ConditionalOnProperty ：当指定的属性有指定的值时进行实例化。
8. @ConditionalOnExpression ：基于SpEL表达式的条件判断。
9. @ConditionalOnJava ：当JVM版本为指定的版本范围时触发实例化。
10. @ConditionalOnResource ：当类路径下有指定的资源时触发实例化。
11. @ConditionalOnJndi ：在JNDI存在的条件下触发实例化。
12. @ConditionalOnSingleCandidate ：当指定的Bean在容器中只有一个，或者有多个但是指定了首选
的Bean时触发实例化。