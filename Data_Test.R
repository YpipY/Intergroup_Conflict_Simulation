setwd("C:/Users/Simon Møller Nielsen/Documents/PresidentialDebates/Tweets")
setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation")

sum(c$ObamaObamaSupport,na.rm = TRUE)
sum(c$RomneyRomneySupport,na.rm = TRUE)
sum(c$ObamaRomneySupport,na.rm = TRUE)
sum(c$RomneyObamaSupport,na.rm = TRUE)
sum(c$Tweets,na.rm = TRUE)

v <- read.table(file = 'Debate1_clean.tsv', sep = '\t', header = TRUE)
c <- read.csv("Tweets1_clean.csv")

c <- read.csv("Debate3_2012_summary.csv")
c2 <- read.csv("Debate2012_3_tidy.csv")

setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation/data")
v2 <-read.csv2("test.csv2")
v1 <-read.csv2("test.csv2")

v<-v1
5*100
500/1000000 *100000
#5562
#5975
#5644
library(ggplot2)
239439 / 469657
sum(v$numberOfRetweets,na.rm = TRUE)
sum(v$numberOfNormalTweets,na.rm = TRUE) + sum(v$numberOfRetweets,na.rm = TRUE)
sum(v$numberOfRetweets,na.rm = TRUE) / (sum(v$numberOfNormalTweets,na.rm = TRUE) + sum(v$numberOfRetweets,na.rm = TRUE))

v <- read.csv2("FullModelNoDefense.csv2")

person <- (as.factor(rep(1, 5644)))
data1 <- data.frame(v[,"tweetCountDem"],v[,"turn"],person)
person <- (as.factor(rep(2, 5644)))
data2 <- data.frame(v[,"tweetCountRep"],v[,"turn"],person)


colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "turns"

ggplot(data, aes(turns,tweetCount))+
  geom_point(aes(colour = factor(person)) , alpha = 0.5)+
  labs(title = "Full Model No Defence",x = "Turn (seconds)", y = "Mentions",color = "Candidate")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))#+
  #xlim(0000, 1000)

ggplot(data, aes(turns,tweetCount))+
  geom_point()+
  labs(color = "Mentions")+
  labs(x = "turn" ,color = "Mentions")+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("blue", "red"))+
  xlim(15, 6000)+
  ylim(1000, 1600)

sd(data1[,"tweetCount"])

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
