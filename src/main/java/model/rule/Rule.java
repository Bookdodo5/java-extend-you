package model.rule;

import model.entity.Entity;
import model.entity.EntityType;
import model.entity.word.EffectType;
import model.entity.word.NounType;
import model.entity.word.PropertyType;
import model.entity.word.VerbType;

import java.util.List;

/**
 * Represents a rule consisting of a subject, verb, effect, and optional conditions.
 */
public class Rule {
    private final EntityType subject;
    private final VerbType verb;
    private final EntityType effect;
    private final Entity subjectText;
    private final Entity verbText;
    private final Entity effectText;
    private final List<Condition> conditions;

    public Rule(Entity subjectText, Entity verbText, Entity effectText, List<Condition> conditions) {
        this.subjectText = subjectText;
        this.verbText = verbText;
        this.effectText = effectText;
        this.conditions = conditions;
        this.subject = ((NounType)subjectText.getType()).getReferencedType();
        this.verb = (VerbType)verbText.getType();

        EffectType effectType = (EffectType)effectText.getType();
        if (effectType instanceof NounType nounType) {
            this.effect = nounType.getReferencedType();
        } else {
            this.effect = effectType;
        }
    }

    public EntityType getSubject() {
        return subject;
    }

    public VerbType getVerb() {
        return verb;
    }

    public EntityType getEffect() {
        return effect;
    }

    public Entity getSubjectText() {
        return subjectText;
    }

    public Entity getVerbText() {
        return verbText;
    }

    public Entity getEffectText() {
        return effectText;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public String getEntitySignature() {
        StringBuilder sb = new StringBuilder();
        sb.append(subjectText.getEntityId());
        sb.append(",");
        sb.append(verbText.getEntityId());
        sb.append(",");
        sb.append(effectText.getEntityId());
        sb.append(",");
        conditions.forEach(c -> {
            sb.append(c.getConditionText().getEntityId());
            sb.append(":");
            sb.append(c.getParameterText().getEntityId());
            sb.append(";");
        });
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.getTypeId());
        sb.append(",");
        sb.append(verb.getTypeId());
        sb.append(",");
        sb.append(effect.getTypeId());
        sb.append(",");
        conditions.forEach(c -> {
            sb.append(c.getCondition().getTypeId());
            sb.append(":");
            sb.append(c.getParameter().getTypeId());
            sb.append(";");
        });
        return sb.toString();
    }
}
