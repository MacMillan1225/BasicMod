package tentianqinyin.relics.origin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import tentianqinyin.character.TenTianQinYin;
import tentianqinyin.relics.BaseRelic;
import tentianqinyin.cards.CustomTags;

import static tentianqinyin.BasicMod.makeID;

public class Rational extends BaseRelic {
    private static final String NAME = Rational.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Rational() {
        super(ID, NAME, TenTianQinYin.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        boolean powerExists = false;

        for(AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow.ID.equals("tentianqinyin:Impressive")) {
                addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, pow, 1));
                break;
            }
        }

        for(AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;
                break;
            }
        }

       if (!powerExists) {
           addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarricadePower(AbstractDungeon.player)));
       }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction){
        int cost = targetCard.costForTurn;
        boolean real_hurt = false;

        for(AbstractCard.CardTags tag : targetCard.tags){
            if (tag == CustomTags.REALCUSM) {
                real_hurt = true;
                break;
            }
        }
        // 如果有真实伤害tag就打真伤
        if (real_hurt) {
            addToTop(new DamageAction(AbstractDungeon.player,
                    new DamageInfo(AbstractDungeon.player, cost, DamageInfo.DamageType.HP_LOSS)
            ));
        }else {
            addToTop(new DamageAction(AbstractDungeon.player,
                    new DamageInfo(AbstractDungeon.player, cost, DamageInfo.DamageType.THORNS)
            ));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
