package de.shiro.actions.recods.config;

import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordTyp;
import de.shiro.system.config.ISession;
import lombok.Getter;

public class RecordGetTypePosActionConfig extends RecordGetActionConfig {

    @Getter
    private final RecordTyp recordTyp;

  public RecordGetTypePosActionConfig(ISession iSession, long from, long to, int page, RecordTyp typ) {
        super(iSession, from, to, page);
        this.recordTyp = typ;
  }

}
