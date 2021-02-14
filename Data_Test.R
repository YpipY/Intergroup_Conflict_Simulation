
setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation")

sum(c$ObamaObamaSupport,na.rm = TRUE)
sum(c$RomneyRomneySupport,na.rm = TRUE)
sum(c$ObamaRomneySupport,na.rm = TRUE)
sum(c$RomneyObamaSupport,na.rm = TRUE)
sum(c$Tweets,na.rm = TRUE)

v <- read.table(file = 'Debate1_clean.tsv', sep = '\t', header = TRUE)
c <- read.csv("Tweets1_clean.csv")

setwd("C:/Users/Simon Møller Nielsen/Documents/PresidentialDebates/Tweets")

c <- read.csv("Debate3_2016_summary.csv")

person <- (as.factor(rep(1, 5506)))
c3 <- data.frame(c[,"Interlocutor"],c[,"TimeTweet"],"tweetCount" = as.numeric(70))
colnames(c3)[1] <- "Interlocutor"
colnames(c3)[2] <- "turns"

c4 <- subset(c3, Interlocutor == "Obama")
c5 <- subset(c3, Interlocutor == "Romney")

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "turns"


setwd("C:/Users/Simon MØller Nielsen/Documents/Intergroup Conflict Simulation/data")
v2 <-read.csv2("test.csv2")
v1 <-read.csv2("test.csv2")

v<-v1

test <- v$numberOfNormalTweets + v$numberOfRetweets
mean(test)
sd(test)
sum(v$numberOfNormalTweets,na.rm = TRUE) + sum(v$numberOfRetweets,na.rm = TRUE)

231778+266801

#5562
#5975
#5644
469657/25000
library(ggplot2)
239439 / 469657
sum(c$Obama,na.rm = TRUE)
sum(c$Romney,na.rm = TRUE)
sum(v$tweetCountDem,na.rm = TRUE)
sum(v$tweetCountRep,na.rm = TRUE)
sum(v$numberOfRetweets,na.rm = TRUE)
sum(v$numberOfNormalTweets,na.rm = TRUE) + sum(v$numberOfRetweets,na.rm = TRUE)
sum(v$numberOfRetweets,na.rm = TRUE) / (sum(v$numberOfNormalTweets,na.rm = TRUE) + sum(v$numberOfRetweets,na.rm = TRUE))
231778+266801



3rdAggMod2.csv2
3rdDeftest3.csv2
3rdMoreReps2.csv2
3rdNettest3.csv2
3rdProInc1.csv2



3rd2016Full.csv2
3rd2016NoReg.csv2
3rd2016NoAgg.csv2
3rd2016NoRet.csv2
3rd2016NoSpe.csv2
3rd2016NoInt.csv2
3rd2016NoMem.csv2
3rd2016NoDec.csv2

v <- read.csv2("3rd2016NoDec.csv2")
person <- (as.factor(rep(1, 5801)))
data1 <- data.frame(v[,"tweetCountDem"],v[,"turn"],person)
person <- (as.factor(rep(2, 5801)))
data2 <- data.frame(v[,"tweetCountRep"],v[,"turn"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "turns"

ggplot(data, aes(turns,tweetCount))+
  geom_point(aes(colour = factor(person)) , alpha = 0.35, size = 1)+
  labs(title = "3rd Debate 2016 No Attention Decay" ,x = "Turn (seconds)", y = "Mentions per turn (count)",color = "Candidate")+
  geom_col(data=c3 , aes(fill = factor(Interlocutor)), alpha = 0.1)+
  scale_fill_manual("Speaker", labels = c("Moderator/Unknown", "Clinton", "Trump", "1", "2" ), values = c("COPPER:" = "white", "RADDATZ:" = "white", "what" = "white", "Clinton:" = "blue", "Trump:" = "red"))+
  scale_color_manual(labels = c("Democrat", "Republican"), values = c("red", "blue"))+
  theme(legend.position = "none", plot.title = element_text(hjust = 0.5))#+
  #ylim(0, 120)

#"3rd Debate 2012 Full Model"
#"3rd Debate 2012 No Regular Tweet Behavior"
#"3rd Debate 2012 No Conversation Seeking Behavior"
#"3rd Debate 2012 No Retweet Behavior"
#"3rd Debate 2012 No Speaker"
#"3rd Debate 2012 No Interruptions"
#"3rd Debate 2012 No Meme Function"
#"3rd Debate 2012 No Attention Decay"

#"3rd Debate 2012 No Network"
#"3rd Debate 2012 No Defense Modifier"

#"3rd Debate 2012 Different Base Agression Chance"
#"3rd Debate 2012 Different Defense Modifier"
#"3rd Debate 2012 Different Number Of Partisans"
#"3rd Debate 2012 Different Network Modifier"
#"3rd Debate 2012 Different Base Tweet Chance"

person <- (as.factor(rep(1, 5506)))
data1 <- data.frame(c[,"Clinton"],c[,"TimeTweet"],person)
person <- (as.factor(rep(2, 5506)))
data2 <- data.frame(c[,"Trump"],c[,"TimeTweet"],person)

colnames(data1)[1] <- "tweetCount"
colnames(data2)[1] <- "tweetCount"

data <- rbind(data1, data2)
colnames(data)[2] <- "turns"

ggplot(data, aes(turns, tweetCount))+
  geom_point(aes(colour = factor(person)) , alpha = 0.35, size = 1)+
  labs(title = "3rd Debate 2016 Real Data", x = "Time (seconds)", y = "Mentions per second (count)",color = "Candidate")+
  geom_col(data=c3 , aes(fill = factor(Interlocutor)), alpha = 0.1)+
  scale_fill_manual("Speaker", labels = c("Moderator/Unknown", "Clinton", "Trump" ), values = c("COPPER:" = "white", "Clinton:" = "blue", "Trump:" = "red"))+
  scale_color_manual(labels = c("Clinton", "Trump"), values = c("blue", "red"))+
  theme(legend.position = "none", plot.title = element_text(hjust = 0.5))

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
