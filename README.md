# lisp_intepreter
一个简单的Lisp语言Scheme方言解释器
## 已实现：
- 整数四则运算、比较大小 `(+ 1 2 3) (> 1 0)`
- 分支语句 `if cond`
- 函数以及闭包 `(define (func x y) (+ x y))`
- lambda表达式 ```(define func (lambda (x) (+ x 1))) (func 1)```
- 惰性求值 `(define (func x y) (if (= x 0) (+ x y) (/ y x)))`
## 待实现：
- 数据抽象
- 浮点运算
