BackGammonAiPlayer
==================

BackGammon With AiPlayer

This is a Java version of Backgammon with AiBot. The basic code (board, error chcecking etc) are from our lecturer's code. Our
group only implemented the AiBot part. We use a unique scoring system to  compute a final score for each plays from 5 aspects:
racing, safety, blitz, and board home. Each board has its own score and all the score will be combined together based on a weigh
we specifically designed for this game. Finally, the one play with highest score would be selected and used for the final play for this 
round. This bot is the winning bot of the final bot competition in the software engineering lecture.
