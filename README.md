# lisp_intepreter
一个基本的scheme方言解释器，目前支持整数的加减乘除、define、lambda表达式、cond、if

## 闭包

## lambda表达式
## 惰性求值
典型应用场景：if else 分支语句、and or 短路求值  
- 分支语句: ```(if (> x 0) (+ x 1) (+ y 1)) ``` 思路：创建单独的惰性求值对象，只对判断条件进行求值
WIP: 数据抽象

