setwd("C:/Users/Simon Møller Nielsen/Documents/Intergroup Conflict Simulation/data")
library(ggplot2)

v <- read.csv2("test4.csv2")


person <- (as.factor(rep(1, 101)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratAggression"],person)
person <- (as.factor(rep(2, 101)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratAggression"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "democratAggression"

ggplot(data, aes(democratAggression,tweetCount))+
  geom_point(aes(colour = factor(person)))

plot(data[,"time"],data[,"tweetCount"])


sum(data$tweetCount)

eq = function(x){1/(1+exp(-x))}
(1/(1+exp(-4)))*100
27255/10000
27255/200

140000/10000
140000/200

10000/10
1/(1+exp(5))
0.01798621*100

plot(eq(-10:10), type = 'l')

?ggplot


eq(-10:10)
digamma(1)
exp(3)
