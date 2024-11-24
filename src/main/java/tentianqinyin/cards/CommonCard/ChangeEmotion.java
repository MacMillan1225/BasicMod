package tentianqinyin.cards.CommonCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tentianqinyin.cards.BaseCard;
import tentianqinyin.character.TenTianQinYin;
import tentianqinyin.util.CardStats;
import tentianqinyin.cards.CustomTags;

public class ChangeEmotion extends BaseCard {
    public static final String ID = makeID(ChangeEmotion.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TenTianQinYin.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            5 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int UPG_COST = 3;
    private static final int DMG_COE = 10;
    private static final int UPG_DMG_COE = 1;

    public ChangeEmotion() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        tags.add(CustomTags.REALCUSM);
        setExhaust(true);
        setMagic(DMG_COE, UPG_DMG_COE);
        setCostUpgrade(UPG_COST);
        this.upgradeDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        this.addToBot(new DamageAllEnemiesAction(p, this.baseDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
            this.damageUpgrade = (int) (AbstractDungeon.player.currentBlock * magicNumber / 10.0F);
            this.baseDamage = (int) (AbstractDungeon.player.currentBlock * magicNumber / 10.0F);
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.initializeDescription();
    }

}
