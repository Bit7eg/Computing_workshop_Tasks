#Репозиторий с решениями задач по предмету "Вычислительный практикум"

##Задача 1:

###Условие:
>__Построение:__  
>Построить функцию f(x), которая определена всюду
на отрезке [a, b] и в некоторых точках этого отрезка
принимает заданные значения:
f(x[i]) = y[i], a = x[0] < x[1] < ... < x[N] = b
(x[i] могут быть расположены на [a, b] неравномерно)  
>- f(x) - непрерывная функция, полином 1-й степени
на каждом интервале [ x[i], x[i+1] ];
>- f(x) - полином N-й степени (в форме Ньютона);
> 
>__Входные параметры__: x[i], y[i], N, x*
>(i = 0, ..., N; x* принадлежит [a, b]);\
>__Результат__: f(x*);
> 
> __Визуальная проверка:__  
> За y[i] принять значения известной функции, то есть 
> положить y[i] = g(x[i]), (g(x[i]), x принадлежит [a, b] 
> задавать в процедуре-функции). Изобразить на одном 
> графике: 
>- интерполируемую функцию g(x);
>- интерполирующую функцию f(x);
>- особо выделить значения в узлах интерполяции.
###Решение:
Для решения задачи построения был написан класс Interpolation.
В этом классе определяются методы putPair() для добавления
новой пары узел-значение, по которым строятся интерполирующие 
функции, lineFunctionY() для вычисления значения 
кусочно-линейной фукнции, которая строится по данным парам, 
polynomialFunctionY() для вычисления значения интерполирующего 
полинома в форме Ньютона и прочие методы для удобной 
работы с объектами этого класса.

Для визуальной проверки использовалась библиотека Java Swing.
Программа рисует 3 графика: интерполируемую функцию, которая
задаётся в методе initContent() класса InterpolationGraphic,
интерполирующий полином для этой функции и кусочно-линейную 
функцию, построенную по узлам интерполяции и значениям 
интерполируемой функции в этих узлах. Также программа 
показывает точки, по которым вычислялись полином и 
кусочно-линейная функция. Кроме того программа предоставляет 
пользователю панель управления, которая позволяет задавать 
минимальное и максимальное значение для области определения 
изображаемых функций, количество узлов интерполяции, и цвет
изображаемых функций и точек, а также фиксировать текущий
масштаб, если это необходимо. Шаг для узлов интерполяции
задаётся в методе initContent() класса InterpolationGraphic.

##Задача 2:

###Условие:
> На отрезке [a, b] задана функция f(x). Найти приближенное
> значение определённого интеграла функции f(x) на отрезке
> [a, b], для чего отрезок [a, b] разбить на N интервалов и
> на каждом из них применить формулу:
>- левых (или правых) прямоугольников;
>- центральных прямоугольников;
>- трапеций;
>- Симпсона;
>- Гаусса (по двум, трём, и т.д. узлам);
> 
> Построить подпрограмму, позволяющую повысить точность
> результата численного интегрирования с помощью вычислений
> на последовательности сеток.

###Решение:



##Задача 3:

###Условие:
> ####Найти все действительные корни уравнения:
> 
> x^3 + a2 * x^2 + a1 * x + a0 = 0
> 
> (известно, что кратных корней нет)
> 
> методом:
>- деления отрезка пополам
>- Ньютона
> 
> ####Входные параметры
> a2, a1, a0 -- коеффициенты уравнения;
> 
> eps -- требуемая точность решения;
> 
> N -- ограничение по числу итераций.
> 
> ####Выходные параметры
> xn -- приближенное решение;
> 
> n -- выполненное количество итераций;
> 
> epsn -- достигнутая точность.

###Решение: