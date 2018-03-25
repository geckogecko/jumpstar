package georg.steinbacher.community_jump_trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseDescription {
    private static final String TAG = "ExerciseDescription";

    private List<ExerciseStep> mSteps;
    private List<Equipment> mEquipmentList;

    ExerciseDescription(List<ExerciseStep> steps, List<Equipment> equipment) throws MissingExerciseStepException {
        //order the steps to be sure they are in the right order.
        mSteps = orderSteps(steps);

        //check if steps start with index 0
        if(mSteps.size() > 0 && mSteps.get(0).getStepNr() != 0)
            throw new MissingExerciseStepException("Steps are not starting with index 0");

        mEquipmentList = equipment;
    }

    public List<ExerciseStep> getSteps() {
        return mSteps;
    }

    public List<Equipment> getNeededEquipment() {
        return mEquipmentList;
    }

    private List<ExerciseStep> orderSteps(List<ExerciseStep> steps) throws MissingExerciseStepException {
        List<ExerciseStep> orderedSteps = new ArrayList<>();
        final int stepsSize = steps.size();

        for (int i = 0; i < stepsSize; i++) {
            boolean stepFound = false;
            for (int j = 0; j < stepsSize; j++) {
                ExerciseStep tempStep = steps.get(j);
                if (tempStep.getStepNr() == i) {
                    orderedSteps.add(i, tempStep);
                    stepFound = true;
                    break;
                }
            }

            if(!stepFound) {
                throw new MissingExerciseStepException("Step nr:" + i + " not found!");
            }
        }

        return orderedSteps;
    }

    public class MissingExerciseStepException extends Exception {
        MissingExerciseStepException(String message) {
            super(message);
        }
    }
}
