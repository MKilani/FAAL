# Created on 22-nov-2018
#
# @author: Kilani Marwan
#
# Python 3.7
#

library (alineR)

#read file

#set location of the file to analyse (the files are provided in the folder: TestsArticle/Datasets/Inputs )

dataset<-"/Users/iome/Desktop/FAAL/TestsArticle/Datasets/Inputs/2_corpus_aline_input_no_alignments.txt"

#set location of output file (the files are provided in the folder: TestsArticle/Datasets/Results )

output<-"/Users/iome/Desktop/FAAL/TestsArticle/Datasets/Results/2_aline_results.txt"

#===============

lines <- scan(dataset, what="", sep="\n")

x<-c(lines[2])
y<-c(lines[3])

for(i in seq(5, length(lines), 3)){

x<-c(x, lines[i])

}
for(i in seq(6, length(lines), 3)){
	
y<-c(y, lines[i])

}

results<-aline(w1=x,w2=y,alignment = TRUE, mark=TRUE)

for(n in 1:ncol(results))
{
for(i in 1:nrow(results)) 
{
  cat(as.character(results[i,n], max.levels=0), sep="\n" )
}
cat(sep="\n" )
}

sink(output)
for(n in 1:ncol(results))
{
for(i in 1:nrow(results)) 
{
  cat(as.character(results[i,n], max.levels=0), sep="\n" )
}
cat(sep="\n" )
}
sink()