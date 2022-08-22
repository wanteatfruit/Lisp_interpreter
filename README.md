# lisp_intepreter
一个基本的scheme方言解释器，目前支持整数的加减乘除

## 闭包

## lambda表达式
## 惰性求值
典型应用场景：if else 分支语句、and or 短路求值  
- 分支语句: ```(if (> x 0) (+ x 1) (+ y 1)) ``` 思路：创建单独的惰性求值对象，只对判断条件进行求值


**遇到嵌套函数时**
(define (add1 x) ((define tmp 1) (define (add) (+ tmp 1)) (add) (+ tmp x)))
(add1 1)
(add1 1)

在add里的tmp值是不是和外面的不一样？

   ((lambda (x y) (+ x y)) 1 2)

   先提前检测lambda，如果检测到，就提前记录下它后面的n个操作数 (可以修改建树？)
(define identity (lambda (x) x))  
