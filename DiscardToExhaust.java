package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiscardToExhaust extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardToExhaust");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private boolean isRandom;

    private boolean anyNumber;

    private boolean canPickZero;

    public static int numExhausted;

    @Override
    public void update() {

    }

    public static class DiscardPileToExhaustAction extends AbstractGameAction {
        public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("DiscardPileToExhaustAction")).TEXT;

        private final AbstractPlayer p;

        public DiscardPileToExhaustAction(int amount) {
            this.p = AbstractDungeon.player;
            setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, amount);
            this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        }

        public void update() {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.discardPile.size() == 1) {
                AbstractCard card = this.p.discardPile.group.get(0);
                this.p.discardPile.moveToExhaustPile(card);
                card.lighten(false);
                this.isDone = true;
                return;
            }

            if (this.duration == 0.5F) {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false);
                tickDuration();
                return;
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.p.discardPile.moveToExhaustPile(c);
                    c.lighten(false);
                    c.unhover();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();

                for (AbstractCard c : this.p.discardPile.group) {
                    c.unhover();
                    c.target_x = CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }
                this.isDone = true;
            }
            tickDuration();
        }
    }}
