package reaper.cards;

import actions.DiscardToExhaust;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import reaper.character.ReaperChar;
import reaper.util.CardStats;

public class GraveStrike extends BaseCard {
    public static final String ID = makeID(reaper.cards.GraveStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ReaperChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );
       private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 4;

    public GraveStrike() {
        super(ID, info);
        tags.add(CardTags.STRIKE);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (!AbstractDungeon.player.discardPile.isEmpty())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        if (p.discardPile.isEmpty()){
            this.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
            return false;
        }
        return true;
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DiscardToExhaust.DiscardPileToExhaustAction(1));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
    @Override
    public AbstractCard makeCopy() { //Optional
        return new reaper.cards.GraveStrike();
    }
}
