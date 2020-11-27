setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation")

v <- read.table(file = 'Debate1_clean.tsv', sep = '\t', header = TRUE)

v <- read.csv("Debate2012_2_tidy.csv")


setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation/data")


library(ggplot2)

v <- read.csv2("test.csv2")

person <- (as.factor(rep(1, 5975)))
data1 <- data.frame(v[,"tweetCountDem"],v[,"turn"],person)
person <- (as.factor(rep(2, 5975)))
data2 <- data.frame(v[,"tweetCountRep"],v[,"turn"],person)


colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "democratAggression"

ggplot(data, aes(democratAggression,tweetCount))+
  geom_point(aes(colour = factor(person)))+
  labs(color = "Mentions")+
  labs(x = "turn" ,color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))

#+
#  xlim(5, 6000)+
#  ylim(200, 650)

########################################

v <- read.csv2("AggressionTest1.csv2")

person <- (as.factor(rep(1, 101)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratAggression"],person)
person <- (as.factor(rep(2, 101)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratAggression"],person)


colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "democratAggression"

ggplot(data, aes(democratAggression,tweetCount))+
  geom_point(aes(colour = factor(person)))+
  labs(color = "Mentions")+
  labs(x = "democratAggression[%]" ,color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))

########################################
v <- read.csv2("DefenceTest1.csv2")

person <- (as.factor(rep(1, 101)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratDefenceModifier"],person)
person <- (as.factor(rep(2, 101)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratDefenceModifier"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratDefenceModifier"

ggplot(data, aes(democratDefenceModifier,tweetCount))+
  geom_point(aes(colour = factor(person)))+
  labs(color = "Mentions")+
  labs(x = "democratDefenceModifier[%]" ,color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))

#########################################
v <- read.csv2("LikelihoodOfTweetingTest1.csv2")
person <- (as.factor(rep(1, 101)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratTweetChance"],person)
person <- (as.factor(rep(2, 101)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratTweetChance"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratTweetChance"

ggplot(data, aes(democratTweetChance,tweetCount))+
  geom_point(aes(colour = factor(person)))+
  labs(x = "democrateTweetChance[???]" ,color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))

#########################################
v <- read.csv2("NetworkTest1.csv2")
person <- (as.factor(rep(1, 501)))
data1 <- data.frame(v[,"tweetCountPerson1"],v[,"democratNetworkModifier"],person)
person <- (as.factor(rep(2, 501)))
data2 <- data.frame(v[,"tweetCountPerson2"],v[,"democratNetworkModifier"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"
data <- rbind(data1, data2)
colnames(data)[2] <- "democratNetworkModifier"

data[,2] <- as.numeric(levels(data[,2]))[data[,2]]

ggplot(data, aes(democratNetworkModifier,tweetCount))+
  geom_point(aes(colour = person)) +
  labs(color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))
  

140000/100000

sum(data$tweetCount)

eq = function(x){1/(1+exp(-x))}
(1/(1+exp(-4)))*100
400/10000
400/200
8/10

(2*8)/((2*8) + 2)

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
