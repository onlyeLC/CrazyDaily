package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.NoteContract;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.note.NoteDeleteNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteGetListNoteUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:31
 * description:
 */
@ActivityScope
public class NotePresenter extends BasePresenter<NoteContract.View> implements NoteContract.Presenter {

    private final NoteGetListNoteUseCase mNoteGetListNoteUseCase;
    private final NoteDeleteNoteUseCase mNoteDeleteNoteUseCase;

    @Inject
    public NotePresenter(NoteGetListNoteUseCase noteGetListNoteUseCase, NoteDeleteNoteUseCase noteDeleteNoteUseCase) {
        mNoteGetListNoteUseCase = noteGetListNoteUseCase;
        mNoteDeleteNoteUseCase = noteDeleteNoteUseCase;
    }

    @Override
    public void deleteNote(Long id) {
        mNoteDeleteNoteUseCase.execute(NoteDeleteNoteUseCase.Params.get(id), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.deleteSuccess(id);
                }
            }
        });
    }

    @Override
    public void getNoteList() {
        mNoteGetListNoteUseCase.execute(new BaseSubscriber<List<NoteEntity>>() {
            @Override
            public void onNext(List<NoteEntity> noteEntities) {
                mView.showNote(noteEntities);
            }
        });
    }
}
