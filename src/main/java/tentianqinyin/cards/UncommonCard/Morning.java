package tentianqinyin.cards.UncommonCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tentianqinyin.cards.BaseCard;
import tentianqinyin.character.TenTianQinYin;
import tentianqinyin.powers.Impressive;
import tentianqinyin.util.CardStats;

public class Morning extends BaseCard {
    public static final String ID = makeID(Morning.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TenTianQinYin.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            4 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DMG = 7;
    private static final int UPG_DMG = 2;
    private static final int IMPRESSIVE = 3;
    private static final int UPG_IMPRESSIVE = 1;

    public Morning() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DMG, UPG_DMG); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("IN", IMPRESSIVE, UPG_IMPRESSIVE);
        colorCustomVar("IN",
                Settings.GREEN_TEXT_COLOR,
                Settings.PURPLE_COLOR,
                Settings.PURPLE_COLOR,
                Settings.GREEN_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,
                new DamageInfo(p,
                        damage,
                        DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        addToBot(new ApplyPowerAction(p, p, new Impressive(p, customVar("IN")), customVar("IN")));
    }


}
