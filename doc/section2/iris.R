data(iris)

pdf('iris-4x4.pdf',width=10,height=10)
plot(iris[,1:4])
dev.off()

pdf('iris-2v3.pdf',width=10,height=10)
plot(iris[,2],iris[,3])
dev.off()

