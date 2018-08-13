package MainProject;

/**
 * Created by abdullah on 12/31/17.
 */
public class Analysis {
    //netWPM = net speed, gross wpm = gross speed
    double grossWPM, netWPM, accuracy,
            totalCharacters, correctCharacters, wrongCharacters,
            timeInMinutes, timeInSeconds,
            score,
            level, requiredAccuracy, requiredNetWPM, targetNetWPM;


//    public Analysis(double timeInMinutes, int totalCharacters, int correctCharacters, int wrongCharacters, int level) {
//        this.timeInMinutes = timeInMinutes;
//        this.totalCharacters = totalCharacters;
//        this.correctCharacters = correctCharacters;
//        this.wrongCharacters = wrongCharacters;
//        this.level = level;
//
//        grossWPM = (totalCharacters /5) / timeInMinutes;
//
//        //handle negative wpm if many mistakes are typed
//        netWPM = Math.ceil( grossWPM - (wrongCharacters/ timeInMinutes) );
////        System.out.println("C " + correctCharacters + "T " +totalCharacters);
////        System.out.println(correctCharacters/ totalCharacters);
////        System.out.println((correctCharacters/ totalCharacters) * 100);
////        System.out.println(Math.ceil((correctCharacters/ totalCharacters) * 100));
////        accuracy = (correctCharacters/ totalCharacters) * 100;
////        accuracy = correctCharacters/totalCharacters;
//        accuracy = Math.ceil( (correctCharacters/ totalCharacters) * 100 );
////        accuracy = 80;
//        timeInSeconds = Math.ceil( timeInMinutes * 60 );
//
////        typecasting to int
////        int n = (int) Math.ceil((double) a / b));
//
////        setting score
////        if((accuracy >= requiredAccuracy) && (netWPM >= requiredNetWPM)){
////            score = Math.ceil( (accuracy * netWPM) - (requiredAccuracy * netWPM) + (netWPM - targetNetWPM) );
////        }
////        else
////            score = 0;
//    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;

        if((this.accuracy >= requiredAccuracy) && (netWPM >= requiredNetWPM)){
            score = Math.ceil( (this.accuracy * netWPM) - (requiredAccuracy * netWPM) + (netWPM - targetNetWPM) );
        }
        else
            score = 0;
    }

    public void setNetWPM(double netWPM) {
        this.netWPM = netWPM;
    }

    public void setGrossWPM(double grossWPM) {
        this.grossWPM = grossWPM;
    }

    public void setTimeInMinutes(double timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
        timeInSeconds = Math.ceil( this.timeInMinutes * 60 );
    }

    public void setCorrectCharacters(double correctCharacters) {
        this.correctCharacters = correctCharacters;
    }

    public void setWrongCharacters(double wrongCharacters) {
        this.wrongCharacters = wrongCharacters;
    }

    public void setTotalCharacters(double totalCharacters) {
        this.totalCharacters = totalCharacters;
    }

    public void setLevel(double level) {
        this.level = level;
        if(this.level == 1){
            requiredAccuracy = 50;
            requiredNetWPM = 3;
            targetNetWPM = 6;
        }

        if(this.level == 2){
            requiredAccuracy = 55;
            requiredNetWPM = 5;
            targetNetWPM = 10;
        }

        if(this.level == 3){
            requiredAccuracy = 60;
            requiredNetWPM = 10;
            targetNetWPM = 20;
        }

        if(this.level == 4){
            requiredAccuracy = 65;
            requiredNetWPM = 20;
            targetNetWPM = 40;
        }
    }
}
