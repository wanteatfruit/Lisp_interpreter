# lisp_intepreter
一个基本的scheme方言解释器，目前支持整数的加减乘除


**遇到嵌套函数时**
(define (add x y) (define (mul x y) (+ x y)))
(add 1 2)
是在调用时再解析第二层 mul
还是在定义时就已经把define完全拆开
    
