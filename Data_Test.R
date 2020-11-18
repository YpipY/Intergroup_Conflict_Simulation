setwd("C:/Users/Simon Møller Nielsen/Documents/Intergroup Conflict Simulation/data")
library(ggplot2)

v <- read.csv2("test.csv2")

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

########################################
v <- read.csv2("test.csv2")

person <- (as.factor(rep(1, 101)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratDefenceModifier"],person)
person <- (as.factor(rep(2, 101)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratDefenceModifier"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratDefenceModifier"

ggplot(data, aes(democratDefenceModifier,tweetCount))+
  geom_point(aes(colour = factor(person)))

#########################################
v <- read.csv2("test.csv2")
person <- (as.factor(rep(1, 100)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratTweetChance"],person)
person <- (as.factor(rep(2, 100)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratTweetChance"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratTweetChance"

ggplot(data, aes(democratTweetChance,tweetCount))+
  geom_point(aes(colour = factor(person)))

#########################################
v <- read.csv2("test.csv2")
person <- (as.factor(rep(1, 501)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratNetworkModifier"],person)
person <- (as.factor(rep(2, 501)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratNetworkModifier"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratNetworkModifier"

ggplot(data, aes(democratNetworkModifier,tweetCount))+
  geom_point(aes(colour = factor(person)))


sum(data$tweetCount)

eq = function(x){1/(1+exp(-x))}
(1/(1+exp(-4)))*100
400/10000
400/200

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
