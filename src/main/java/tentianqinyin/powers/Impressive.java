package tentianqinyin.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static tentianqinyin.BasicMod.makeID;

public class Impressive extends BasePower {
    public static final String POWER_ID = makeID(Impressive.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Impressive(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.owner = owner;
        this.amount = amount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        //If NORMAL (attack) damage, modify damage by this power's amount
        addToBot(new DamageRandomEnemyAction(
                new DamageInfo(this.owner,
                        this.amount,
                        DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
