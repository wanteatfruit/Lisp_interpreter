# lisp_interpreter
一个简单的Lisp语言Scheme方言解释器
## 已实现：
- 整数四则运算、比较大小 `(+ 1 2 3) (> 1 0)`
- 分支语句 `if cond`
- 函数以及闭包 `(define (func x y) (+ x y))`
- lambda表达式 ```(define func (lambda (x) (+ x 1))) (func 1)```
- 分支语句的惰性求值 `(define (func x y) (if (= x 0) (+ x y) (/ y x)))`
- 逻辑与或的短路求值 `(and (< 1 0) (= 1 1))`
## 待实现：
- 数据抽象
- 浮点运算
## 使用示例：
```java=
(+ 1 1)
2
(define x 1)
(+ x 1)
2
(define (func a b) (+ a b))
(func 1 2)
3
-1 //exit
```
## 已知bug:
- 负数、零支持不完善
