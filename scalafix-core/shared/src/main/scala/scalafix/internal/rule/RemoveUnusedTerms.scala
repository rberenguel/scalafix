package scalafix.internal.rule

import scala.meta._
import scalafix.Patch
import scalafix.SemanticdbIndex
import scalafix.rule.RuleCtx
import scalafix.rule.SemanticRule

case class RemoveUnusedTerms(index: SemanticdbIndex)
    extends SemanticRule(index, "RemoveUnusedTerms") {

  private val unusedTerms = {
    val UnusedLocalVal = """local (.*) is never used""".r
    index.messages.toIterator.collect {
      case Message(pos, _, UnusedLocalVal(_*)) =>
        pos
    }.toSet
  }

  private def isUnused(defn: Defn) =
    unusedTerms.contains(defn.pos)

  private def removeDeclarationTokens(i: Defn, rhs: Term): Tokens = {
    val startDef = i.tokens.start
    val startBody = rhs.tokens.start
    i.tokens.take(startBody - startDef)
  }

  private def tokensToRemove(defn: Defn): Option[Tokens] = defn match {
    case i @ Defn.Val(_, _, _, Lit(_)) => Some(i.tokens)
    case i @ Defn.Val(_, _, _, rhs) => Some(removeDeclarationTokens(i, rhs))
    case i @ Defn.Var(_, _, _, Some(Lit(_))) => Some(i.tokens)
    case i @ Defn.Var(_, _, _, rhs) => rhs.map(removeDeclarationTokens(i, _))
    case _ => None
  }

  override def fix(ctx: RuleCtx): Patch =
    ctx.tree.collect {
      case i: Defn if isUnused(i) =>
        tokensToRemove(i).fold(Patch.empty)(ctx.removeTokens)
    }.asPatch
}
