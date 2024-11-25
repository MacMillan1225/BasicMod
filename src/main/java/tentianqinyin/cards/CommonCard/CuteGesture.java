package tentianqinyin.cards.CommonCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tentianqinyin.cards.BaseCard;
import tentianqinyin.cards.CustomTags;
import tentianqinyin.character.TenTianQinYin;
import tentianqinyin.powers.Impressive;
import tentianqinyin.util.CardStats;

public class CuteGesture extends BaseCard {
    public static final String ID = makeID(CuteGesture.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TenTianQinYin.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            5 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int IMPRESSIVE = 2;
    private static final int UPG_IMPRESSIVE = 1;

    private static final int DMG_COE = 10;
    private static final int UPG_DMG_COE = 2;

    public CuteGesture() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setCustomVar("IN", IMPRESSIVE, UPG_IMPRESSIVE);
        colorCustomVar("IN",
                Settings.GREEN_TEXT_COLOR,
                Settings.PURPLE_COLOR,
                Settings.PURPLE_COLOR,
                Settings.GREEN_TEXT_COLOR);

        setExhaust(true);
        setMagic(DMG_COE, UPG_DMG_COE);
        this.upgradeDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        addToBot(new DamageAllEnemiesAction(p, this.baseDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new Impressive(p, customVar("IN")), customVar("IN")));
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
